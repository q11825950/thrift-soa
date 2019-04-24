package com.github.mql.rpc.loadbalance;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Random;

/** 
 * @Description  随机的负载均衡算法
 * @ClassName   RandomLoadBalance 
 * @Date        2017年11月14日 下午10:24:30 
 * @Author      dn-jack 
 */

public class RandomLoadBalance implements LoadBalance {
    
    public NodeInfo doSelect(List<String> registryInfo) {
        Random random = new Random();
        int index = random.nextInt(registryInfo.size());
        String registry = registryInfo.get(index);
        
        JSONObject registryJo = JSONObject.parseObject(registry);
       /* Collection values = registryJo.values();
        JSONObject node = new JSONObject();
        for (Object value : values) {
            node = JSONObject.parseObject(value.toString());
        }
        
        JSONObject protocol = node.getJSONObject("protocol");*/
        NodeInfo nodeinfo = new NodeInfo();
        nodeinfo.setHost(registryJo.get("host") != null ? registryJo.getString("host"): "");
        nodeinfo.setPort(registryJo.get("port") != null ? registryJo.getString("port"): "");
        nodeinfo.setContextpath(registryJo.get("contextpath") != null ? registryJo.getString("contextpath"): "");
        return nodeinfo;
    }

    public static void main(String[] args) {
        String registry = "{\"192.0.0.1\":8080,\"192.0.0.2\":8080}";
        JSONObject registryJo = JSONObject.parseObject(registry);
        System.out.println(registry);
    }
}
