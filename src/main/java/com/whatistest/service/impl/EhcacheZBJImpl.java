package com.whatistest.service.impl;

import com.whatistest.service.EhcacheZBJ;
import com.whatistest.util.common.YMLUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by rubyvirusqq@gmail.com on 23/05/17.
 * <p>
 * 添加测试数据到缓存中
 * <p>
 */
@Service(value = "ehcacheZBJService")
public class EhcacheZBJImpl implements EhcacheZBJ, Serializable {

    // 存在一个问题，数据还在缓存中
    @Cacheable(value = "zbjData", key = "#classN")
    @Override
    public LinkedHashMap addZBJDataEhcache(String classN, String dataFile) {
        return YMLUtil.getInstance(dataFile).getZBJData();
    }

    // 存储配置数据
    @Cacheable(value = "zbjConfig", key = "#classN")
    @Override
    public LinkedHashMap addZBJConfigEhcache(String classN, String dataFile) {
        return YMLUtil.getInstance(dataFile).getZBJData();
    }
}
