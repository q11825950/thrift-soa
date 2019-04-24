package com.github.mql.rpc.proxy;

import com.github.mql.rpc.configBean.Reference;
import com.github.mql.rpc.invoke.Invoke;

import java.lang.reflect.Proxy;

public class RpcConsumerProxy
{
	public Object proxy(Invoke invoke, Class<?> iFaceInterface, Reference referenceBean){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return Proxy.newProxyInstance(classLoader, new Class[]{iFaceInterface}, new InvokeInvocationHandler(invoke,iFaceInterface,referenceBean));
	}

	/*public Object proxy(Class<?> iFaceInterface){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final Class<?> factoryClass = getFactoryClass(iFaceInterface);
		final String serviceName = iFaceInterface.getDeclaringClass().getName();
		return Proxy.newProxyInstance(classLoader, new Class[] { iFaceInterface }, new InvocationHandler() {

			@SuppressWarnings("unchecked")
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
				String key = "localhost:9898";
				Object result = null;
				TSocket tsocket = null;
				try{
					TServiceClientFactory<TServiceClient> clientFactory = (TServiceClientFactory<TServiceClient>) factoryClass.newInstance();
					tsocket = pool.borrowObject(key);
					TTransport transport = new TFramedTransport(tsocket);
					TProtocol protocol = new TBinaryProtocol(transport);
					TMultiplexedProtocol mpProtocol = new TMultiplexedProtocol(protocol, serviceName);
					TServiceClient client = clientFactory.getClient(mpProtocol);
					result = method.invoke(client, args);
				} catch (Exception e){
					if(!InvocationTargetException.class.isInstance(e)){
						if(null != tsocket){
							pool.invalidateObject(key, tsocket);
						}
						throw e;
					}
					InvocationTargetException inve = (InvocationTargetException) e;
					if(!TApplicationException.class.isInstance(inve.getTargetException())){
						throw e;
					}

					TApplicationException appe = (TApplicationException)inve.getTargetException();
					if(appe.getType() != TApplicationException.MISSING_RESULT){
						throw e;
					}
					result = null;

				}finally{
					if( null != tsocket){
						pool.returnObject(key, tsocket);
					}
				}
				return result;
			}

		});
	}*/

	/*private Class<?> getFactoryClass(Class<?> iFaceInterface)
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
	}*/
}
