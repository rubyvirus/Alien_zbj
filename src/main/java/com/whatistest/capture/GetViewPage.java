package com.whatistest.capture;

import com.whatistest.util.zbjbusiness.token.ConfluenceToken;
import io.restassured.http.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

/**
 * Created by rubyvirusqq@gmail.com on 09/06/17.
 * jira:
 * rap:
 * description: 获取接口详细信息
 */
public class GetViewPage {

    private Cookie cookie = ConfluenceToken.getInstance().getToken();

    private static class page {
        private static final GetViewPage gvp = new GetViewPage();
    }

    public static GetViewPage getInstance() {
        return page.gvp;
    }


    /**
     * 获取接口名
     *
     * @param pageId
     */
    public String getInterfaceName(String pageId) {
        Document document = Jsoup.parse(given().cookie(cookie).get(ConfluenceToken.getInstance().temp.get("url").toString() + "/pages/viewpage.action?pageId=" + pageId).getBody().asString());
        String text = document.select("h3").text();
        String interfaceName = text.substring(text.lastIndexOf(".") + 1, text.length());
        return interfaceName;
    }


    /**
     * 获取所有子节点，整个页面内容
     */
    private List<Elements> getAllContent(String pageId) {
        List<String> requestURLList = GetNaturalChildren.getChildren(pageId).stream().map(str -> str += "&src=contextnavpagetreemode").collect(Collectors.toList());

        // 每个页面所有数据
        List<String> resultViewPage = requestURLList.stream().map(str -> given().cookie(cookie).get(ConfluenceToken.getInstance().temp.get("url").toString() + str).getBody().asString()).collect(Collectors.toList());

        // 获取main-content主要内容
        List<Elements> elements = resultViewPage.stream().map((str -> Jsoup.parse(str).select("div[id=\"main-content\"]"))).collect(Collectors.toList());

        return elements;
    }

    /**
     * 解析页面所有内容
     * 页面必须符合规则，否则无法解析
     * 1. 获取“入参”的位置
     * 2. 获取“出参”的位置
     * 3. 获取两个位置中间的表格内容在解析
     *
     * @param pageId
     * @return
     */
    public List<List<LinkedHashMap>> parseViewPage(String pageId) {
        // 遍历爬取所有内容
        List allList = this.getAllContent(pageId).stream().map(elements -> Arrays.asList(new Map[]{parseViewPageCommon(elements), parseViewPageBusiness(elements)})).collect(Collectors.toList());

        return allList;
    }

    /**
     * 解析单个页面业务参数
     * 返回结构：
     * 字段｜类型
     *
     * @param elements
     */
    private Map<String, Object> parseViewPageBusiness(Elements elements) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        int[] inputParamsIndex = {0};
        int[] outputParamsIndex = {0};
        // 获取“入参”的位置　获取“出参”的位置
        elements.first().childNodes().stream().filter(node -> node.toString() != "").forEach(node -> {
            if (node.toString().contains("入参：")) {
                inputParamsIndex[0] = node.siblingIndex();
            } else if (node.toString().contains("出参：")) {
                outputParamsIndex[0] = node.siblingIndex();
            }
        });

        // 获取全部表格
        Elements tableEl = elements.select("div[class=\"table-wrap\"]");
        // 获取入参详细内容
        List<Element> elementList = tableEl.stream().filter(element -> element.siblingIndex() > inputParamsIndex[0] && element.siblingIndex() < outputParamsIndex[0]).collect(Collectors.toList());

        // 解析所有入参
        elementList.stream().forEach(element -> element.select("table").select("tr").stream().filter(tr -> tr.select("td").size() > 0).forEach(tr -> {
            Elements tds = tr.select("td");
            resultMap.put(tds.get(0).text(), tds.get(1).text());
        }));

        return resultMap;
    }

    /**
     * 解析单个页面公共参数
     * 返回结构：
     * requestURL|""
     * requestType"""
     */
    private Map<String, Object> parseViewPageCommon(Elements elements) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        // 获取全部表格
        Elements tableEl = elements.select("div[class=\"table-wrap\"]");
        // 解析requestPath, post, methodName
        Map<String, Object> commonMap = new HashMap<>();
        tableEl.first().select("table").select("tr").stream().forEach(tr -> {
            Elements tds = tr.select("td");
            commonMap.put(tds.get(0).text(), tds.get(1).text());
        });
        String method = commonMap.get("方法签名(缩略)").toString();
        String key = method.substring(method.lastIndexOf(">") + 1, method.indexOf("(")).trim();
        resultMap.put("requestPath", commonMap.get("请求链接URL"));
        resultMap.put("requestMethod", key.replace(key.charAt(0), String.valueOf(key.charAt(0)).toUpperCase().charAt(0)));
        resultMap.put("requestType", commonMap.get("请求类型"));
        return resultMap;
    }

}
