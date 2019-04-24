package com.github.mql.rpc.provider;

import com.github.mql.rpc.configBean.Service;
import com.github.mql.rpc.registry.BaseRegistryDelegate;
import com.github.mql.rpc.thrift.ThriftUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.github.mql.rpc.thrift.ThriftServer.getIfaceClass;

/** 
* @Description: 注册服务提供者
* @Author: 孟庆霖
* @Date: 2018/12/21 
*/
public class RpcServerRegistry implements InitializingBean, DisposableBean,ApplicationContextAware
{
	private static final Logger logger = LoggerFactory.getLogger(RpcServerRegistry.class);

	private Map<String, RpcService> serviceMap = new HashMap<>();

	public ApplicationContext application;


	@Override
	public void afterPropertiesSet() throws Exception{
		//获取指定注解的bean
		Map<String, Object> beans = application.getBeansWithAnnotation(RpcService.class);
		if (beans == null || beans.size() == 0)
		{
			throw new IllegalStateException("No Thrift Service Found.");
		}
//		zkClient = new ZkClient(serverList);

		Service service = application.getBean(Service.class);

		Set<Map.Entry<String, Object>> beanEntrySet = beans.entrySet();
		for (Map.Entry<String, Object> entry : beanEntrySet)
		{
			Object bean = entry.getValue();
			RpcService ann = bean.getClass().getAnnotation(RpcService.class);
			Class<?> ifaceClass = getIfaceClass(bean);
			String serviceName = ifaceClass.getEnclosingClass().getName();
			serviceMap.put(serviceName, ann);
		}

		BaseRegistryDelegate.registry(serviceMap,application);

        if(service.getServerProtocol().equals("thrift")){

            ThriftUtil.startThriftServer(beans, service.getPort());
        }

		//启动时注册
		/*register();
		
		zkClient.subscribeStateChanges(new IZkStateListener() {
			@Override
			public void handleStateChanged(KeeperState state) throws Exception
			{
				//断开重连之后从新注册
				if(state.equals(KeeperState.SyncConnected))
				{
					register();
				}
			}
			@Override
			public void handleNewSession() throws Exception
			{
			}

			@Override
			public void handleSessionEstablishmentError(Throwable error) throws Exception
			{
			}
		});*/
	}

	@Override
	public void destroy() throws Exception
	{
		ThriftUtil.stopServer();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.application=applicationContext;
	}

/*	private void register()
	{
		Set<Map.Entry<String, ThriftService>> annEntrySet = serviceMap.entrySet();
		for (Map.Entry<String, ThriftService> entry : annEntrySet)
		{
			ThriftService ann = entry.getValue();
			String serviceName = entry.getKey();
			String version = ann.version();
			String address = serverIp + ":" + serverPort + ":" + ann.weight();
			String servicePath = "/rpc/" + serviceName + "_" + version;
			String addressPath = servicePath + "/" + address;
			
			if (!zkClient.exists(servicePath))
			{
				zkClient.createPersistent(servicePath, true);
			}
			zkClient.createEphemeral(addressPath);
			if (logger.isInfoEnabled())
			{
				logger.info(String.format("Service provider (%s) registed", addressPath));
			}
		}
	}*/



/*	public String getServerIp()
	{
		return serverIp;
	}

	public void setServerIp(String serverIp)
	{
		this.serverIp = serverIp;
	}

	public int getServerPort()
	{
		return serverPort;
	}

	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}*/

//	public String getServerList()
//	{
//		return serverList;
//	}
//
//	public void setServerList(String serverList)
//	{
//		this.serverList = serverList;
//	}
}
