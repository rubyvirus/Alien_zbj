package com.whatistest.testng.listeners;

import com.whatistest.testng.exception.ErrorRespStatusException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;


public class TestNGRetry implements IRetryAnalyzer {
    private static int retryCount = 1;
    private static int maxRetryCount = 2;

    public boolean retry(ITestResult result) {
        // TODO Auto-generated method stub
        if (result.getThrowable() instanceof ErrorRespStatusException && retryCount % maxRetryCount != 0) {
            Reporter.setCurrentTestResult(result);
            Reporter.log("RunCount=" + (retryCount + 1));
            retryCount++;
            return true;
        } else {
            resetRetryCount();
            return false;
        }
    }

    public static void resetRetryCount() {
        retryCount = 1;
    }
}

