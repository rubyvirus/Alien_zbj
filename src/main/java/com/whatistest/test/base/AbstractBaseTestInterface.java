package com.whatistest.test.base;

import org.springframework.test.context.ContextConfiguration;

/**
 * Created by rubyvirusqq@gmail.com on 16/05/17.
 * <p>
 * 加载springContext，测试使用
 */
@ContextConfiguration(locations = {"classpath:springContext.xml"})
public interface AbstractBaseTestInterface {
}
