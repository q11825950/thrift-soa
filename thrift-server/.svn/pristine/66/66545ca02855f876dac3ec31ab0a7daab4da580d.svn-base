package com.github.mql.rpc.consumer;


import com.github.mql.rpc.configBean.Reference;
import com.github.mql.rpc.constants.ConsumerConstant;
import com.github.mql.rpc.proxy.RpcConsumerProxy;
import com.github.mql.rpc.registry.BaseRegistryDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

public class RpcRefAnnBeanPostProcessor implements BeanPostProcessor,InitializingBean,ApplicationContextAware
{
	private static final Logger logger = LoggerFactory.getLogger(RpcRefAnnBeanPostProcessor.class);

	public ApplicationContext application;
	private RpcConsumerProxy proxy;



	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
	{
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods)
		{
			String name = method.getName();
			if (name.length() > 3 && name.startsWith("set") && method.getParameterTypes().length == 1 && Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()))
			{
				try
				{
					RpcReference reference = method.getAnnotation(RpcReference.class);
					if (reference != null)
					{
						Object value = refer(reference, method.getParameterTypes()[0]);
						if (value != null)
						{
							method.invoke(bean, new Object[] {});
						}
					}
				} catch (Throwable e)
				{
					logger.error("Failed to init remote service reference at method " + name + " in class " + bean.getClass().getName() + ", cause: " + e.getMessage(), e);
				}
			}
		}
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			try
			{
				if (!field.isAccessible())
				{
					field.setAccessible(true);
				}
				RpcReference reference = field.getAnnotation(RpcReference.class);
				if (reference != null)
				{
					Object value = refer(reference, field.getType());
					if (value != null)
					{
						field.set(bean, value);
					}
				}
			} catch (Throwable e)
			{
				logger.error("Failed to init remote service reference at filed " + field.getName() + " in class " + bean.getClass().getName() + ", cause: " + e.getMessage(), e);
			}
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
	{
		return bean;
	}

	/** 
	* @Description: 获取服务端代理
 	* @param reference
 	* @param referenceClass
	* @return: java.lang.Object 
	* @Author: 孟庆霖
	* @Date: 2018/12/21 
	*/
	private Object refer(RpcReference reference, Class<?> referenceClass){

		Reference referenceBean = application.getBean(Reference.class);
		referenceBean.setLoadbalance(reference.loadbalance());
		referenceBean.setCluster(reference.clusters());
		referenceBean.setServerKey(reference.serverKey().trim());
		return proxy.proxy(ConsumerConstant.invokes.get(referenceBean.getProtocol()),referenceClass,referenceBean);
	}

	public RpcConsumerProxy getProxy(){
		return proxy;
	}

	public void setProxy(RpcConsumerProxy proxy)
	{
		this.proxy = proxy;
	}


	/** 
	* @Description: 从注册中心获取服务端地址
  	* @param
	* @return: void 
	* @Author: 孟庆霖
	* @Date: 2018/12/21 
	*/
	public void afterPropertiesSet() throws Exception {
		Map<String,List<String>> registry = BaseRegistryDelegate.getRegistry(application);
		Reference referenceBean = application.getBean(Reference.class);
		referenceBean.setServerRegistry(registry);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.application=applicationContext;
	}

}
