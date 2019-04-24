package com.github.mql.rpc.configBean;

import com.github.mql.rpc.registry.BaseRegistry;
import com.github.mql.rpc.registry.PropertyRegistry;
import com.github.mql.rpc.registry.RedisRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;


public class Registry implements InitializingBean,
        ApplicationContextAware {

    private static final long serialVersionUID = 45234109234L;

    public ApplicationContext application;

    /*支持的注册中心*/
    public Map<String, BaseRegistry> registryMap = new HashMap<String, BaseRegistry>();

    /*注册中心类型*/
    private String protocol;

    /*注册中心地址*/
    private String address;

    
    public void setApplicationContext(ApplicationContext applicationContext){
        this.application = applicationContext;
        
    }

    
    public void afterPropertiesSet() throws Exception {
        registryMap.put("property", new PropertyRegistry());
        registryMap.put("redis", new RedisRegistry());
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, BaseRegistry> getRegistryMap() {
        return registryMap;
    }

    public void setRegistryMap(Map<String, BaseRegistry> registryMap) {
        this.registryMap = registryMap;
    }
}
