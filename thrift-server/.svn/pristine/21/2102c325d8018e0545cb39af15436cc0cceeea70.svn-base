package com.github.mql.rpc.registry;

import com.github.mql.rpc.provider.RpcService;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

public interface BaseRegistry {
    public boolean registry(Map<String, RpcService> serviceMap, ApplicationContext application);
    
    public Map<String,List<String>> getRegistry(ApplicationContext application);
}
