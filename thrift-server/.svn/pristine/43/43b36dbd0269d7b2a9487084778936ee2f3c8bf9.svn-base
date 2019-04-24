package com.example.controller;

import com.github.mql.rpc.consumer.RpcReference;
import com.github.mql.rpc.thrift.ThriftUtil;
import com.github.thrift.service1.order.OrderService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ConsumerService
{

	@RpcReference(version="1.1.1")
	OrderService.Iface orderClient;
	
	@RequestMapping("/comsume")
	@ResponseBody
	public void consume() throws TException
	{
//		orderClient.getById(1l);
		System.out.println("comsume");
		ThriftUtil.stopServer();
	}
}
