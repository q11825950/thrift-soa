package com.github.mql.rpc.registry;

import com.alibaba.fastjson.JSONObject;
import com.github.mql.rpc.configBean.Reference;
import com.github.mql.rpc.provider.RpcService;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @Description 这个是property的注册中心处理类
 * @ClassName   PropertyRegistry
 * @Date
 * @Author      mql
 */
public class PropertyRegistry implements BaseRegistry {


    /**
    * @Description: 无实现注册
     * @param serviceMap
     * @param application
    * @return: boolean
    * @Author: 孟庆霖
    * @Date: 2018/12/21
    */
    public boolean registry(Map<String, RpcService> serviceMap, ApplicationContext application) {

        return false;
    }
    


    /** 
    * @Description: 获取消费端的服务
     * @param application 
    * @return: java.util.List<java.lang.String> 
    * @Author: 孟庆霖
    * @Date: 2018/12/21 
    */
    public Map<String,List<String>> getRegistry(ApplicationContext application) {
        Reference referenceBean = application.getBean(Reference.class);
        String servletList = referenceBean.getServletList();

        if(servletList!=null && !"".equals(servletList)){
            Map<String,List<String>> serverRegistry = new HashMap<>();
            String[] serverArray = servletList.split(",");
            for (String serverHost : serverArray) {
                //test=192.168.1.82:9000|192.168.1.83:9000
                String[] keyAndHost = serverHost.split("=");
                if(keyAndHost!=null && keyAndHost.length>1){
                    List<String> list = new ArrayList<>();
                    String key = keyAndHost[0];
                    //192.168.1.82:9000|192.168.1.83:9000
                    String[] hostPort = keyAndHost[1].split("\\|");
                    if(hostPort!=null){
                        Map<String,String> map = new HashMap<>();
                        for(String hostPortStr : hostPort){
                            String[] hostAndport = hostPortStr.split(":");
                            if(hostAndport!=null && hostAndport.length>1){
                                map.put("host",hostAndport[0].trim());
                                map.put("port",hostAndport[1].trim());
                                list.add(JSONObject.toJSONString(map));
                                map.clear();
                            }else{
                                throw new RuntimeException("服务端地址和端口信息错误！");
                            }
                        }
                    }
                    serverRegistry.put(key.trim(),list);
                }else{
                    throw new RuntimeException("服务端地址和端口信息错误！");
                }
            }
            return serverRegistry;
        }
        return null;
    }
}
