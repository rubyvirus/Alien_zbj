package com.whatistest.util.common;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 字符串工具类集成Common-lang3
 */

public class StringUtil extends StringUtils implements Serializable {

    public static boolean isNotEmpty(String str) {
        return null != str && !"".equals(str);
    }

    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * @param sourceStr  待替换字符串
     * @param matchStr   匹配字符串
     * @param replaceStr 目标替换字符串
     * @return
     */
    public static String replaceFirst(String sourceStr, String matchStr, String replaceStr) {
        int index = sourceStr.indexOf(matchStr);
        int matLength = matchStr.length();
        int sourLength = sourceStr.length();
        String beginStr = sourceStr.substring(0, index);
        String endStr = sourceStr.substring(index + matLength, sourLength);
        sourceStr = beginStr + replaceStr + endStr;
        return sourceStr;
    }

    /**
     * 转换请求的地址
     *
     * @param baseURL
     * @param server
     * @return
     */
    public static String convertPath(String baseURL, String server) {
        return baseURL.replace("server", server);
    }
}
