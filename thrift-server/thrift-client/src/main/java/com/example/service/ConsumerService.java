package com.example.service;

import com.example.thrift.api.HelloService;
import com.github.mql.rpc.consumer.RpcReference;
import com.github.thrift.service1.user.UserDTO;
import com.github.thrift.service1.user.UserService;
import com.xxce.service.dbserver.SyncRequestService;
import org.apache.commons.codec.language.Soundex;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.ByteBuffer;


@Controller
public class ConsumerService
{

	/*@RpcReference(version="1.1.1",serverKey = "test")
	SyncRequestService.Iface dbService;

	@RpcReference
	UserService.Iface userService;*/


	@RpcReference(serverKey = "test")
	SyncRequestService.Iface syncRequestService;


	@RequestMapping("/test")
	@ResponseBody
	public void test(String aa ) throws TException{
		ByteBuffer byteBuffer = syncRequestService.SyncRequest(ByteBuffer.wrap(aa.getBytes()), 1);
		System.out.println(new String(byteBuffer.array()));
	}


/*	public void consume() throws TException
	{
		String aaa = dbService.SyncRequest("aaa", 1);
		System.out.println(aaa);
	}

	@RequestMapping("/comsumeUser")
	@ResponseBody
	public void consumeUser() throws TException
	{
		UserDTO userDTO = userService.getById(1);
		System.out.println(userDTO.name);
	}*/
}
