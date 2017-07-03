package com.whatistest.testng.listeners;

import com.whatistest.testng.dataprovider.DataProviderFactory;
import com.whatistest.testng.annotation.ZBJTestDataProvider;
import com.whatistest.util.common.DataProviderUtil;
import com.whatistest.util.common.StringUtil;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {
    @SuppressWarnings("rawtypes")
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            annotation.setRetryAnalyzer(TestNGRetry.class);
        }

        // 如果标签是ZBJTestData,就设置case测试数据
        if (testMethod.isAnnotationPresent(ZBJTestDataProvider.class)) {
            ZBJTestDataProvider zbjTestDataProvider = testMethod.getAnnotation(ZBJTestDataProvider.class);
            if ("dataprovider".equalsIgnoreCase(zbjTestDataProvider.dataSaveType())) {
                this.dataProviderX(testMethod, annotation);
            }
        }
    }


    /**
     * 设置dataProvider
     *
     * @param testMethod
     * @param annotation
     */
    private void dataProviderX(Method testMethod, ITestAnnotation annotation) {
        // 根据添加的文件，调用相应的文件解析器并设置当前Method的dataProvider
        String dp = DataProviderFactory.getDataProvider(testMethod);
        if (StringUtil.isNotBlank(dp)) {
            annotation.setDataProvider(dp);
            annotation.setDataProviderClass(DataProviderUtil.class);
        } else {
            System.out.println("加载测试数据失败.目标类： " + this.getClass().getName());
        }
    }
}

