package com.github.mql.rpc.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 技术部03 on 2018/12/21.
 */
public class ThriftServer {

    /** 
    * @Description: 创建thrift服务
     * @param beans
 * @param port 
    * @return: org.apache.thrift.server.TServer 
    * @Author: 孟庆霖
    * @Date: 2018/12/21 
    */
    public static TServer creatThriftServer (Map<String, Object> beans, String port) throws TTransportException {
        //非堵塞IO传输通道
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(Integer.parseInt(port));
        //多线程网络服务类型
        TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);

        //多路复用处理器
        TMultiplexedProcessor processor = new TMultiplexedProcessor();

        Set<Map.Entry<String, Object>> beanEntrySet = beans.entrySet();
        for (Map.Entry<String, Object> entry : beanEntrySet)
        {
            Object bean = entry.getValue();
            Class<?> ifaceClass = getIfaceClass(bean);
            TProcessor processItem = getServiceProcessor(bean, ifaceClass);
            String serviceName = ifaceClass.getEnclosingClass().getName();
            System.out.println(serviceName);
            //向TMultiplexedProcessor注册TProcessor
            processor.registerProcessor(serviceName, processItem);
        }

        tArgs.processorFactory(new TProcessorFactory(processor));
        //传输层类型（非阻塞方式，按块的大小进行传输）
        tArgs.transportFactory(new TFramedTransport.Factory());
        // 二进制协议工厂
        tArgs.protocolFactory(new TBinaryProtocol.Factory(true, true));
        // 工作线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        tArgs.executorService(pool);

        //多线程服务器服务端类型
        return new TThreadedSelectorServer(tArgs);
    }


    /**
     * 获取thrift的Iface接口
     * @param bean
     * @return
     */
    public static Class<?> getIfaceClass(Object bean)
    {
        Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(bean);
        for (Class<?> item : allInterfaces)
        {
            if (!item.getSimpleName().equals("Iface"))
            {
                continue;
            }
            return item;
        }
        return null;
    }


    /**
     * 获取TProcessor
     * @param bean
     * @param ifaceClazz
     * @return
     */
    public static TProcessor getServiceProcessor(Object bean, Class<?> ifaceClazz)
    {
        try
        {
            Class<TProcessor> processorClazz = null;
            //得到目标方法所在的class类
            Class<?> clazz = ifaceClazz.getDeclaringClass();
            Class<?>[] classes = clazz.getDeclaredClasses();
            for (Class<?> innerClazz : classes)
            {
                if (!innerClazz.getName().endsWith("$Processor"))
                {
                    continue;
                }
                //判断是否实现了TProcessor接口
                if (!TProcessor.class.isAssignableFrom(innerClazz))
                {
                    continue;
                }
                processorClazz = (Class<TProcessor>) innerClazz;
                break;
            }
            if (processorClazz == null)
            {
                throw new IllegalStateException("No TProcessor Found.");
            }
            //获得对应类的构造器（构造方法）
            Constructor<TProcessor> contructor = processorClazz.getConstructor(ifaceClazz);
            //通过构造器创建对象
            return BeanUtils.instantiateClass(contructor, bean);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
