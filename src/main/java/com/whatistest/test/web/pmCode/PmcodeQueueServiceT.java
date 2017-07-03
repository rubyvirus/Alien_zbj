package com.whatistest.test.web.pmCode;

import com.whatistest.dubbo.DubboServiceFactory;
import com.whatistest.test.base.DubboAbstractBaseTest;
import org.testng.annotations.BeforeClass;

/**
 * Created by rubyvirusqq@gmail.com on 21/06/17.
 * jira:
 * rap:
 * description: pmcode统计基础类
 */
public class PmcodeQueueServiceT extends DubboAbstractBaseTest {

    @BeforeClass
    @Override
    public void loadData() {
        super.loadData();
        DubboServiceFactory.getDubboServiceFactory().getService(null);
    }
}
