package com.github.mql.rpc.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 技术部03 on 2018/12/21.
 */
public class ThriftUtil extends Thread{

    private static ServerThread thriftTask;

    /**线程池**/
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();



    public static void startThriftServer(Map<String, Object> beans, String port) throws TTransportException {
        //需要单独的线程,因为serve方法是阻塞的.
        thriftTask = new ServerThread(beans, port);
        executorService.execute(thriftTask);
    }



    public static void stopServer() {
        if (thriftTask!=null){
            thriftTask.stopServer();
        }
    }


    private static final class ServerThread implements Runnable
    {
        private TServer server;

        ServerThread(Map<String, Object> beans,String port) throws TTransportException{
            server = ThriftServer.creatThriftServer(beans, port);
        }

        @Override
        public void run(){
            if (server != null && !server.isServing()){
                server.serve();
            }
        }

        public void stopServer(){
            if (server != null && server.isServing()){
                server.stop();
            }
        }
    }

}
