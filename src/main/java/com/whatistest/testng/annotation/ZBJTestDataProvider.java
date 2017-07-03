package com.whatistest.testng.annotation;

import java.lang.annotation.*;

/**
 * Created by rubyvirusqq@gmail.com on 18/05/17.
 * 定义加载测试数据的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
public @interface ZBJTestDataProvider {
    enum params {
        DATAFILE, SQLQUERY, SHEETNAME
    }

    enum dataProviders {
        is_yml, is_excel, is_database
    }

    // 提供数据方式
    enum dataType {
        EHCACHE, DATAPROVIDER
    }


    // 数据文件路径
    String dataFile() default "";

    // 配置文件路径
    String configFile() default "ZBJConfig.yml";

    // sql语句，查询数据
    String sqlQuery() default "";

    // 数据文件为excel,获取那个具体sheet数据
    String sheetName() default "";

    // 数据方式
    String dataSaveType() default "ehcache";

}
