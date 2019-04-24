package com.github.mql.rpc.loadbalance;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Description 轮询的负载均衡算法
 * @ClassName   RoundRobinLoadBalance
 * @Date        2017年11月14日 下午10:25:41
 * @Author      dn-jack
 */

public class RoundRobinLoadBalance implements LoadBalance {

    private static Integer index = 0;

    public NodeInfo doSelect(List<String> registryInfo) {
        synchronized (index) {
            if (index >= registryInfo.size()) {
                index = 0;
            }
            String registry = registryInfo.get(index);
            index++;
            JSONObject registryJo = JSONObject.parseObject(registry);
            /*Collection values = registryJo.values();
            JSONObject node = new JSONObject();
            for (Object value : values) {
                node = JSONObject.parseObject(value.toString());
            }

            JSONObject protocol = node.getJSONObject("protocol");*/
            NodeInfo nodeinfo = new NodeInfo();
            nodeinfo.setHost(registryJo.get("host") != null ? registryJo.getString("host") : "");
            nodeinfo.setPort(registryJo.get("port") != null ? registryJo.getString("port") : "");
            nodeinfo.setContextpath(registryJo.get("contextpath") != null ? registryJo.getString("contextpath") : "");
            return nodeinfo;
        }
    }
}
