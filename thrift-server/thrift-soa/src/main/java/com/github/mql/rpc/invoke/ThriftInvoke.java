package com.github.mql.rpc.invoke;


import com.github.mql.rpc.thrift.KeyPoolFactory;
import com.github.mql.rpc.thrift.ThriftSocketPoolFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class ThriftInvoke implements Invoke {
    private static final Logger logger = LoggerFactory.getLogger(ThriftInvoke.class);
    /*链接对象线程池*/
//    GenericKeyedObjectPool<String, TSocket> pool = new GenericKeyedObjectPool<>(new ThriftSocketPoolFactory());

    @Override
    public Object invoke(Invocation invocation,String host_port) throws Exception {


        Class<?> factoryClass = getFactoryClass(invocation.getReferenceClass());

        Object result;
        TSocket tsocket = null;
        try{

            //获取key对应的链接
//            tsocket = pool.borrowObject(host_port);
            tsocket = KeyPoolFactory.getTSocket(host_port);
            logger.info("获取客户端起请求链接socket"+host_port+"-----"+tsocket);

            TServiceClientFactory<TServiceClient> clientFactory = (TServiceClientFactory<TServiceClient>) factoryClass.newInstance();
            TTransport transport = new TFramedTransport(tsocket);
            TProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol mpProtocol = new TMultiplexedProtocol(protocol, invocation.getReferenceClass().getDeclaringClass().getName());
            TServiceClient client = clientFactory.getClient(mpProtocol);
            result = invocation.getMethod().invoke(client, invocation.getObjs());
            logger.info("接口调用返回："+result);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("thrift客户端请求异常："+e.getMessage());
            if(InvocationTargetException.class.isInstance(e)){
                InvocationTargetException inve = (InvocationTargetException) e;

                if(TTransportException.class.isInstance(inve.getTargetException())){
                    System.out.println("链接异常");
                    if(null != tsocket){
                        KeyPoolFactory.getPool().invalidateObject(host_port, tsocket);
                        tsocket=null;
                    }

                }

            }
            throw e;



        }finally {
            if( null != tsocket)
            {
                KeyPoolFactory.returnBean(host_port,tsocket);
            }
        }


        return result;
    }


    private Class<?> getFactoryClass(Class<?> iFaceInterface)
    {
        Class<?> factoryClass = null;
        try
        {
            Class<?> serviceClass = iFaceInterface.getDeclaringClass();
            factoryClass = iFaceInterface.getClassLoader().loadClass(serviceClass.getName() + "$Client$Factory");
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return factoryClass;
    }
}
