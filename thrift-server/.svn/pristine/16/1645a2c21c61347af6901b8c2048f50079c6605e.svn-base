package com.github.mql.rpc.cluster;

import com.github.mql.rpc.configBean.Reference;
import com.github.mql.rpc.constants.ConsumerConstant;
import com.github.mql.rpc.invoke.Invocation;
import com.github.mql.rpc.invoke.Invoke;
import com.github.mql.rpc.loadbalance.LoadBalance;
import com.github.mql.rpc.loadbalance.NodeInfo;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.transport.TTransportException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description 自动切换，重试
 * 失败自动切换，当出现失败，重试其它服务器。(缺省)
 * 通常用于读操作，但重试会带来更长延迟。
 * @ClassName FailoverClusterInvoke
 * @Date 2019/1/3
 * @Author mql
 */
public class FailoverClusterInvoke implements Cluster {

    private static Set<String> hostPortSet = new HashSet<>();

    @Override
    public Object invoke(Invocation invocation) throws Exception {
        Invoke invoke = invocation.getInvoke();
        hostPortSet.clear();

        return handle(invocation, invoke);
    }

    private Object handle(Invocation invocation, Invoke invoke) throws Exception {

        List<String> registryInfo = getRegistryInfoByServerKey(invocation);

        String host_port = getHostPort(invocation,registryInfo);


        //服务失败后换其他服务
        int count=0;
        while (hostPortSet.contains(host_port)){
            if(count>6){
                throw new RuntimeException(registryInfo+"全部失败！！！！");
            }
            host_port = getHostPort(invocation,registryInfo);
            count++;
        }

        //每个服务失败重试2次
        for(int i=0;i<2;i++){
            try {
                return invoke.invoke(invocation,host_port);
            }catch (Exception e){
                if(TTransportException.class.isInstance(e)){
                    continue;
                }

                if(InvocationTargetException.class.isInstance(e)){
                    InvocationTargetException inve = (InvocationTargetException) e;
                    if(TApplicationException.class.isInstance(inve.getTargetException())){
                        TApplicationException appe = (TApplicationException)inve.getTargetException();
                        if(appe.getType() == TApplicationException.MISSING_RESULT){
                            System.out.println("返回参数异常，");
                            e.printStackTrace();
                            return null;
                        }
                    }
                    if(TTransportException.class.isInstance(inve.getTargetException())){
                        continue;
                    }


                }
                throw e;
            }
        }

        //已失败服务集合
        hostPortSet.add(host_port);

        if(hostPortSet.size()>=registryInfo.size()){
            throw new RuntimeException(registryInfo+"全部失败！！！！");
        }

        return handle(invocation, invoke);
    }

    /**
    * @Description: 获取服务列表的某一个服务
    * @param invocation
    * @return: java.lang.String
    * @Author: 孟庆霖
    * @Date: 2019/1/4
    */
    public String getHostPort(Invocation invocation,List<String> registryInfo) {
        //获取负载均衡算法类型
        String loadbalance = invocation.getReferenceBean().getLoadbalance();
        LoadBalance loadbalanceBean = ConsumerConstant.loadBalances.get(loadbalance);

        NodeInfo nodeinfo = loadbalanceBean.doSelect(registryInfo);
        return nodeinfo.getHost()+":"+nodeinfo.getPort();
    }


    public List<String> getRegistryInfoByServerKey(Invocation invocation){
        Reference referenceBean = invocation.getReferenceBean();
        List<String> registryInfo = referenceBean.getServerRegistry().get(referenceBean.getServerKey());
        return registryInfo;
    }


}
