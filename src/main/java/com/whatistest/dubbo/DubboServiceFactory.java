package com.whatistest.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.whatistest.util.common.YMLUtil;


/**
 * dubbo服务工厂类
 * 本例为单例模式
 */
public class DubboServiceFactory {


    private ApplicationConfig application;
    private RegistryConfig registry;


    public DubboServiceFactory() {
        application = new ApplicationConfig();
        application.setName("zbjTest-application");
        // 连接注册中心配置
        registry = new RegistryConfig();
        registry.setAddress("zookeeper://" + YMLUtil.getInstance("src/main/resources/properties/zbj.yml").loadYML().get("zookeeper"));
        registry.setUsername("zbjTester");
    }

    private static class DubboServiceFactorySingleton {
        public final static DubboServiceFactory dubboServiceFactory = new DubboServiceFactory();
    }

    public static DubboServiceFactory getDubboServiceFactory() {
        return DubboServiceFactorySingleton.dubboServiceFactory;
    }

    /**
     * 获取远程服务代理
     *
     * @param clazz
     * @param version
     * @param <T>
     * @return
     */
    public <T extends Object> T getService(Class<T> clazz, String... version) {

        // 引用远程服务
        ReferenceConfig<T> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        if (version != null && version.length != 0) {
            reference.setVersion(version[0]);
        }

        reference.setGeneric(Boolean.FALSE);
        reference.setTimeout(10000);
        reference.setInterface(clazz);
        ReferenceConfigCache referenceConfigCache = ReferenceConfigCache.getCache();
        T t = referenceConfigCache.get(reference);

        return t;
    }

}
