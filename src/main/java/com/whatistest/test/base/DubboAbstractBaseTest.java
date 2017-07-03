package com.whatistest.test.base;

import org.testng.annotations.BeforeClass;

/**
 * Created by rubyvirusqq@gmail.com on 14/06/17.
 * jira:
 * rap:
 * description: web项目测试基础类
 */

public class DubboAbstractBaseTest extends AbstractBaseTest {


    @BeforeClass
    @Override
    public void loadData() {
        super.loadData();
    }

}
