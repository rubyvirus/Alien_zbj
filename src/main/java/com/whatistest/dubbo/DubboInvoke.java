package com.whatistest.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.whatistest.util.common.YMLUtil;

/**
 * Created by rubyvirusqq@gmail.com on 19/06/17.
 * jira:
 * rap:
 * description:
 */
public class DubboInvoke {

    public <T extends Object> T invoke(Class<T> tClass) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("zbjWirelessTest-consumer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://" + YMLUtil.getInstance("src/main/resources/ZBJConfig.yml").loadYML().get("zookeeper").toString());
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(tClass);
        referenceConfig.setGeneric(Boolean.TRUE);
        ReferenceConfigCache referenceConfigCache = ReferenceConfigCache.getCache();
        GenericService genericService = referenceConfigCache.get(referenceConfig);
        T result = (T) genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"rubyvirus2"});
        return result;
    }


}
