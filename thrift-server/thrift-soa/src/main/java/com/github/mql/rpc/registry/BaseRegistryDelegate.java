package com.github.mql.rpc.registry;

import com.github.mql.rpc.configBean.Registry;
import com.github.mql.rpc.provider.RpcService;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

public class BaseRegistryDelegate {



    /**
     * 向注册中心注册服务
     * @param serviceMap
     * @param application
     */
    public static void registry(Map<String, RpcService> serviceMap, ApplicationContext application) {
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry registryBean = registry.getRegistryMap().get(protocol);
        registryBean.registry(serviceMap, application);
    }
    
    /** 
    * @Description: 获取注册中心的服务地址
     * @param application 
    * @return: java.util.List<java.lang.String> 
    * @Author: 孟庆霖
    * @Date: 2018/12/21 
    */
    public static Map<String,List<String>> getRegistry(ApplicationContext application) {
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry registryBean = registry.getRegistryMap().get(protocol);
        return registryBean.getRegistry(application);
    }
}
