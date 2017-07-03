package com.whatistest.test.base;

import com.whatistest.service.EhcacheZBJ;
import com.whatistest.spring.ZBJTestExecutionListener;
import com.whatistest.testng.annotation.ZBJTestDataProvider;
import com.whatistest.util.common.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rubyvirusqq@gmail.com on 16/05/17.
 * <p>
 * 添加自定义spring监听器ZBJTestExecutionListener.class
 */
@ZBJTestDataProvider(configFile = "")
@TestExecutionListeners(value = ZBJTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@Transactional(value = "zbjTransactionManager")
@Commit
public abstract class AbstractBaseTest extends AbstractTransactionalTestNGSpringContextTests implements AbstractBaseTestInterface, Serializable {

    @Autowired
    public EhcacheZBJ ehcacheZBJ;

    public LinkedHashMap allDataFromEhcache;

    public LinkedHashMap allConfigFromEhcache;

    @BeforeClass
    public void loadData() {
        allDataFromEhcache = ehcacheZBJ.addZBJDataEhcache(this.getClass().getName(), "");
        allConfigFromEhcache = ehcacheZBJ.addZBJConfigEhcache(this.getClass().getName(), "");
    }

    /**
     * 返回Object类型数据
     *
     * @param rule
     */
    public Object postData(String rule) {
        return SearchUtil.getStringValue(allDataFromEhcache, rule);
    }

    /**
     * 返回Map<String, Object>类型数据
     *
     * @param rule
     * @return
     */
    public Map<String, Object> getData(String rule) {
        return (Map<String, Object>) SearchUtil.getStringValue(allDataFromEhcache, rule);
    }

    /**
     * 获取加载的配置
     *
     * @param rule
     */
    public String getConfig(String rule) {
        return SearchUtil.getStringValue(allConfigFromEhcache, rule).toString();
    }

}
