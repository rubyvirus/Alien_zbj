package com.whatistest.util.common;

import com.jayway.jsonpath.JsonPath;

import java.util.LinkedHashMap;

/**
 * Created by rubyvirusqq@gmail.com on 31/05/17.
 * 将map转化为json，搜索内容
 */
public class SearchUtil {

    public static Object getStringValue(LinkedHashMap tempMap, String rule) {
        return JsonPath.read(tempMap, rule);
    }
}
