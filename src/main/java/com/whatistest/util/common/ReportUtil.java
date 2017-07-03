package com.whatistest.util.common;

import org.testng.Reporter;

public class ReportUtil {
    private static String reportName = "ZBJ_Mobile自动化测试报告";

    public static void log(String msg) {
        Reporter.log(msg, true);
    }

    public static String getReportName() {
        return reportName;
    }

    public static void setReportName(String reportName) {
        if (StringUtil.isNotEmpty(reportName)) {
            ReportUtil.reportName = reportName;
        }
    }
}

