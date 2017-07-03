package com.whatistest.test.${testPath};

import com.alibaba.fastjson.JSON;
import com.whatistest.testng.annotation.ZBJTestDataProvider;
import io.restassured.http.ContentType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
* rap:
* jira:
*/
@ZBJTestDataProvider()
//@Sql(value = {"classpath:"})
//@Rollback()
public class ${requestClass} extends <extended> {

    @BeforeClass
    @Override
    public void loadData() {
    super.loadData();
    }

    <#list requestMethod as str>
    @Test(description = "")
    public void ${str}() {

        // 添加入参到日志
        Reporter.log(getData("$.${str}").toString(), true);
        given().contentType(ContentType.JSON).body(getData("$.${str}")).
        when().post(getConfig("$.requestPath.newboss_industry_refundAccount").toString()).then();

    }
    </#list>

}
