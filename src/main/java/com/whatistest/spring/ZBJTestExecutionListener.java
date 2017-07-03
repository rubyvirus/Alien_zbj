package com.whatistest.spring;

import com.whatistest.service.EhcacheZBJ;
import com.whatistest.testng.annotation.ZBJTestDataProvider;
import com.whatistest.util.common.StringUtil;
import com.whatistest.util.common.YMLUtil;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Created by rubyvirusqq@gmail.com on 24/05/17.
 * <p>
 * 自定义springframework test module listener
 * <p>
 * 在测试类开始之前加载测试数据到ehcache
 * 参考spring原生监听器SqlScriptsTestExecutionListener.class
 * 继承AbstractTestNGSpringContextTests抽象类，加载ZBJTestExecutionListener，合并策略兼容
 */
public class ZBJTestExecutionListener extends AbstractTestExecutionListener {


    // 注入配置的文件路径
    private String testDataPath = YMLUtil.getInstance("src/main/resources/properties/zbj.yml").loadYML().get("testData").toString();

    /**
     * 测试类前，加载测试数据到ehcache
     *
     * @param testContext
     * @throws Exception
     */
    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        // 如果当前测试类包含ZBJTestDataProvider注解，执行如下操作
        if (testContext.getTestClass().isAnnotationPresent(ZBJTestDataProvider.class)) {
            // 获取bean
            EhcacheZBJ ehcacheZBJ = testContext.getApplicationContext().getBean(EhcacheZBJ.class);
            // class类反射添加的注解
            ZBJTestDataProvider zbjTestDataProvider = testContext.getTestClass().getAnnotation(ZBJTestDataProvider.class);
            // 数据保存方式为ehcache，不区分大小写，保存数据到ehcache。并检查文件格式和文件是否存在
            if (ZBJTestDataProvider.dataType.EHCACHE.name().equalsIgnoreCase(zbjTestDataProvider.dataSaveType())) {
                /**
                 *此处支持两种方式
                 * 1. 名称中含有多个/，表示全路径并检查是否为文件
                 * 2. 名称中没有/，表示只有文件名
                 */

                // 数据文件路径，默认不传入文件路径，使用当前类名
                String dataFile = StringUtil.isNotEmpty(zbjTestDataProvider.dataFile()) ? zbjTestDataProvider.dataFile() : testContext.getTestClass().getSimpleName() + ".yml";
                String configFile = zbjTestDataProvider.configFile();
                // 检查数据文件路径"/"字符出现的数量
                int symbolCount = StringUtil.countMatches(dataFile, "/");
                if (StringUtil.isNotBlank(dataFile) && CheckFile.checkFilesType(dataFile)) {
                    if (symbolCount == 0 && StringUtil.isNotBlank(testDataPath) && CheckFile.checkDirs(testDataPath)) {
                        String dataPath = SearchFile.searchFileExists(testDataPath, dataFile);
                        String configPath = SearchFile.searchFileExists(testDataPath, configFile);
                        if (StringUtil.isNotBlank(dataPath) && StringUtil.isNotBlank(configPath)) {
                            ehcacheZBJ.addZBJDataEhcache(testContext.getTestClass().getName(), dataPath);
                            ehcacheZBJ.addZBJConfigEhcache(testContext.getTestClass().getName(), configPath);
                        }
                    } else if (symbolCount > 0 && CheckFile.checkFiles(dataFile)) {
                        ehcacheZBJ.addZBJDataEhcache(testContext.getTestClass().getName(), dataFile);
                        ehcacheZBJ.addZBJConfigEhcache(testContext.getTestClass().getName(), configFile);
                    }
                }

            }
        }
        super.beforeTestClass(testContext);

    }

    /**
     * 测试case之前加载测试数据到ehcache
     *
     * @param testContext
     * @throws Exception
     */
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        // 如果当前测试方法包含ZBJTestDataProvider注解，执行如下操作
        if (testContext.getTestMethod().isAnnotationPresent(ZBJTestDataProvider.class)) {
            // 获取bean
            EhcacheZBJ ehcacheZBJ = testContext.getApplicationContext().getBean(EhcacheZBJ.class);
            // method方法反射添加的注解
            ZBJTestDataProvider zbjTestDataProvider = testContext.getTestMethod().getAnnotation(ZBJTestDataProvider.class);
            // 数据保存方式为ehcache，不区分大小写，保存数据到ehcache
            if (ZBJTestDataProvider.dataType.EHCACHE.name().equalsIgnoreCase(zbjTestDataProvider.dataSaveType())) {
                /**
                 *此处支持两种方式
                 * 1. 名称中含有多个/，表示全路径并检查是否为文件
                 * 2. 名称中没有/，表示只有文件名
                 */
                // 数据文件路径
                String dataFile = zbjTestDataProvider.dataFile();
                String configFile = zbjTestDataProvider.configFile();
                // 检查数据文件路径"/"字符出现的数量
                int symbolCount = StringUtil.countMatches(dataFile, "/");
                if (StringUtil.isNotBlank(dataFile) && CheckFile.checkFilesType(dataFile)) {
                    if (symbolCount == 0 && StringUtil.isNotBlank(testDataPath) && CheckFile.checkDirs(testDataPath)) {
                        String dataPath = SearchFile.searchFileExists(testDataPath, dataFile);
                        String configPath = SearchFile.searchFileExists(testDataPath, configFile);
                        if (StringUtil.isNotBlank(dataPath) && StringUtil.isNotBlank(configPath)) {
                            ehcacheZBJ.addZBJDataEhcache(testContext.getTestMethod().getName(), dataPath);
                            ehcacheZBJ.addZBJConfigEhcache(testContext.getTestClass().getName(), configPath);
                        }
                    } else if (symbolCount > 0 && CheckFile.checkFiles(dataFile)) {
                        ehcacheZBJ.addZBJDataEhcache(testContext.getTestMethod().getName(), dataFile);
                        ehcacheZBJ.addZBJConfigEhcache(testContext.getTestClass().getName(), configFile);
                    }
                }

            }
        }
        super.beforeTestMethod(testContext);
    }
}
