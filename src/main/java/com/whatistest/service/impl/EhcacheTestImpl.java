package com.whatistest.service.impl;

import com.whatistest.service.EhcacheTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by rubyvirusqq@gmail.com on 15/05/17.
 */
@Service(value = "cacheService")
public class EhcacheTestImpl implements EhcacheTest {
    @Cacheable(value = "cacheTest", key = "#param")
    @Override
    public String getTimestamp(String param, String name) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString() + add(name);
    }

    public String add(String name) {
        return name + "123";
    }
}
