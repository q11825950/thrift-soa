package com.github.mql.rpc.cluster;


import com.github.mql.rpc.invoke.Invocation;

/** 
* @Description: 容错机制
* @Author: 孟庆霖
* @Date: 2019/1/3 
*/
public interface Cluster {
    public Object invoke(Invocation invocation) throws Exception;
}
