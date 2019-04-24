package com.github.mql.rpc.cluster;

import com.github.mql.rpc.configBean.Reference;
import com.github.mql.rpc.constants.ConsumerConstant;
import com.github.mql.rpc.invoke.Invocation;
import com.github.mql.rpc.invoke.Invoke;
import com.github.mql.rpc.loadbalance.LoadBalance;
import com.github.mql.rpc.loadbalance.NodeInfo;

import java.util.List;

/**
 * @Description 快速失败模式
 * 快速失败，只发起一次调用，失败立即报错。
 * 通常用于非幂等性的写操作，比如新增记录。
 * @ClassName FailfastClusterInvoke
 * @Date 2019/1/3
 * @Author mql
 */
public class FailfastClusterInvoke implements Cluster {
    @Override
    public Object invoke(Invocation invocation) throws Exception {
        try {
            String host_port = getHostPort(invocation);
            Invoke invoke = invocation.getInvoke();
            return invoke.invoke(invocation,host_port);

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * @Description: 获取服务列表的某一个服务
     * @param invocation
     * @return: java.lang.String
     * @Author: 孟庆霖
     * @Date: 2019/1/4
     */
    private String getHostPort(Invocation invocation) {
        //这个是负载均衡算法
        String loadbalance = invocation.getReferenceBean().getLoadbalance();
        LoadBalance loadbalanceBean = ConsumerConstant.loadBalances.get(loadbalance);
        Reference referenceBean = invocation.getReferenceBean();
        List<String> registryInfo = referenceBean.getServerRegistry().get(referenceBean.getServerKey());


        NodeInfo nodeinfo = loadbalanceBean.doSelect(registryInfo);
        return nodeinfo.getHost()+":"+nodeinfo.getPort();
    }
}
