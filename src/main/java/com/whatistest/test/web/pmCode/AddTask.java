package com.whatistest.test.web.pmCode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.whatistest.test.base.DubboAbstractBaseTest;
import com.whatistest.testng.annotation.ZBJTestDataProvider;
import com.zhubajie.common.dto.Result;
import com.zhubajie.market.api.dto.pmcodeQueue.quicktask.request.TaskReq;
import com.zhubajie.market.api.service.PmcodeQueueService;
import javafx.concurrent.Task;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Reporter.log;

/**
 * Created by rubyvirusqq@gmail.com on 21/06/17.
 * jira:
 * rap:
 * description: 订单pmcode统计
 */

@ZBJTestDataProvider()
public class AddTask extends PmcodeQueueServiceT {

    /**
     * 加载测试数据
     */
    @BeforeClass
    @Override
    public void loadData() {
        super.loadData();
    }

    public TaskReq convertJavaBean(String string) {
        return JSON.parseObject(getData("$." + string).toString(), TaskReq.class);
    }


    /**
     * 测试用例集
     */

    @Test(description = "正常数据")
    public void addTask() {
        TaskReq taskReq = convertJavaBean("addTask");
        log(getData("$.addTask").toString(), true);
        Assert.assertEquals(JSON.toJSONString(pmcodeQueueService.addTask(taskReq)), "{\"success\":true}");
    }

}
