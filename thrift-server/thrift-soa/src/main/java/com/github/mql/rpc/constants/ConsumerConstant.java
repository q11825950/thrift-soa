package com.github.mql.rpc.constants;

import com.github.mql.rpc.cluster.Cluster;
import com.github.mql.rpc.cluster.FailfastClusterInvoke;
import com.github.mql.rpc.cluster.FailoverClusterInvoke;
import com.github.mql.rpc.cluster.FailsafeClusterInvoke;
import com.github.mql.rpc.invoke.Invoke;
import com.github.mql.rpc.invoke.ThriftInvoke;
import com.github.mql.rpc.loadbalance.LoadBalance;
import com.github.mql.rpc.loadbalance.RandomLoadBalance;
import com.github.mql.rpc.loadbalance.RoundRobinLoadBalance;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 消费端常量初始化
 * @ClassName ConsumerConstant
 * @Date 2019/1/3
 * @Author mql
 */
public class ConsumerConstant {
    /**
     * 消费端rpc调用方式集合
     */
    public static Map<String, Invoke> invokes = new HashMap<String, Invoke>();
    /**
     * 负载均衡集合
     */
    public static Map<String, LoadBalance> loadBalances = new HashMap<String, LoadBalance>();


    public static Map<String, Cluster> clusters = new HashMap<String, Cluster>();

    static {
        invokes.put("thrift", new ThriftInvoke());

        loadBalances.put("random", new RandomLoadBalance());//随机
        loadBalances.put("roundrob", new RoundRobinLoadBalance());//轮训

        clusters.put("failover", new FailoverClusterInvoke());
        clusters.put("failfast", new FailfastClusterInvoke());
        clusters.put("failsafe", new FailsafeClusterInvoke());
    }
}
