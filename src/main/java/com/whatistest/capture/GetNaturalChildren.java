package com.whatistest.capture;

import com.whatistest.util.zbjbusiness.token.ConfluenceToken;
import io.restassured.http.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

/**
 * Created by rubyvirusqq@gmail.com on 09/06/17.
 * jira:
 * rap:
 * description: 获取子节点url链接
 * 目前只支持子节点无子节点的页面
 */
public class GetNaturalChildren {


    /**
     * 获取所有子节点
     */
    public static List<String> getChildren(String pageId) {
        // 获取请求参数
        Object[] objects = GetAncestorInfo.getAncestorInfo(pageId);
        Cookie cookie = ConfluenceToken.getInstance().getToken();
        Map<String, Object> paramsMap = (Map<String, Object>) objects[0];
        List<Object> ancestors = (List<Object>) objects[1];

        // 拼接请求链接
        String requestURL = ConfluenceToken.getInstance().temp.get("url").toString() + paramsMap.get("treeRequestId").toString() + "&hasRoot=true&pageId=" + paramsMap.get("rootPageId").toString() + "&treeId=0&startDepth=0&treePageId=" + paramsMap.get("treePageId").toString() + "&mobile=false";
        for (Object o : ancestors) {
            requestURL += "&ancestors=" + o.toString();
        }

        // 转化为document，筛选url
        Document document = Jsoup.parse(given().cookie(cookie).get(requestURL).getBody().asString());
        Elements elements = document.select("ul[id=\"child_ul" + pageId + "-0\"]");
        Elements hrefs = elements.select("a[href]");

        List<String> resultHref = hrefs.stream().map(element -> element.attr("href")).collect(Collectors.toList());

        return resultHref;
    }
}
