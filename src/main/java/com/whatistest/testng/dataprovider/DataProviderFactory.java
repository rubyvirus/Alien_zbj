package com.whatistest.testng.dataprovider;

import com.whatistest.testng.annotation.ZBJTestDataProvider;
import com.whatistest.util.common.StringUtil;
import org.testng.ITestClass;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rubyvirusqq@gmail.com on 16/05/17.
 * <p>
 * 使用@Test(dataProvider=)方式加载数据工厂类
 */
public class DataProviderFactory {

    /**
     * 根据文件类型，返回annotationName
     *
     * @param method
     * @return
     */
    public static String getDataProvider(Method method) {
        String dp = null;
        Map<String, String> properties = getParameters(method);
        if (!properties.isEmpty()) {
            if (properties.containsKey(ZBJTestDataProvider.params.DATAFILE.name())) {
                String dataFile = properties.get(ZBJTestDataProvider.params.DATAFILE.name());
                dp = getFilePostfix(dataFile);
            }
        }
        return dp;
    }

    /**
     * 返回文件类型
     *
     * @param str
     * @return
     */
    private static String getFilePostfix(String str) {
        String dp;
        if (str.endsWith(".xls") || str.endsWith(".xlsx")) {
            dp = ZBJTestDataProvider.dataProviders.is_excel.name();
        } else if (str.endsWith(".yml") || str.endsWith("yaml")) {
            dp = ZBJTestDataProvider.dataProviders.is_excel.name();
        } else {
            throw new RuntimeException("暂时不支持您传入的文件类型，加载测试数据");
        }
        return dp;
    }

    /**
     * 读取ZBJTestDataProvider注解填写的值
     * <p>
     * 以键值对方式存储，如：key: DATAFILE, value:
     *
     * @param method
     * @return
     */
    protected static Map<String, String> getParameters(Method method) {
        Map<String, String> properties = null;
        if (method.isAnnotationPresent(ZBJTestDataProvider.class)) {
            properties = new HashMap<>();
            ZBJTestDataProvider zbjTestDataProvider = method.getAnnotation(ZBJTestDataProvider.class);
            if (StringUtil.isNotBlank(zbjTestDataProvider.dataFile())) {
                properties.put(ZBJTestDataProvider.params.DATAFILE.name(), zbjTestDataProvider.dataFile());
            } else {
                throw new RuntimeException("ZBJTestDataProvider注解必须添加dataFile变量.");
            }
            if (StringUtil.isNotBlank(zbjTestDataProvider.sheetName())) {
                properties.put(ZBJTestDataProvider.params.SHEETNAME.name(), zbjTestDataProvider.sheetName());
            }
        }
        return properties;
    }

    public static String getDataProvider(ITestClass iTestClass) {
        return ZBJTestDataProvider.dataProviders.is_yml.name();
    }
}
