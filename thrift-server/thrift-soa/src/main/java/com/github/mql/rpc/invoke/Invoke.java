package com.github.mql.rpc.invoke;

public interface Invoke {

    public Object invoke(Invocation invocation,String host_port) throws Exception;
}
