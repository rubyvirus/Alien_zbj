package com.whatistest.capture;

import io.restassured.http.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

/**
 * Created by rubyvirusqq@gmail.com on 08/06/17.
 * jira:
 * rap:
 * description: 获取查询子节点的基础信息
 */
public class GetAncestorInfo {

    public static Object[] getAncestorInfo(String pageId) {
        // 需要修改
        Cookie cookie = null;
        String html = given().cookie(cookie).get("/pages/viewpage.action?pageId=" + pageId).getBody().asString();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("fieldset[class=\"hidden\"]");
        Map<String, Object> resultMap = elements.first().getAllElements().stream().limit(13).filter(element -> element.attr("value") != "").collect(Collectors.toMap(element -> element.attr("name"), element -> element.attr("value")));
        List resultList = elements.last().getAllElements().stream().map(element -> element.attr("value")).filter(s -> s != "").collect(Collectors.toList());
        return new Object[]{resultMap, resultList};
    }
}
