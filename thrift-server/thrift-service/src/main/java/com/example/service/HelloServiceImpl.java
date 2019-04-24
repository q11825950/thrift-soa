package com.example.service;

import com.example.thrift.api.HelloService;
import com.github.mql.rpc.provider.RpcService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by henry on 2018/7/23.
 */
@Service
@RpcService(version="1.1.2", weight=3)
public class HelloServiceImpl implements HelloService.Iface {


    @Override
    public ByteBuffer greet(ByteBuffer name) throws TException {
        String aa = null;
        try {
            aa = new  String(name.array(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(aa);

        return ByteBuffer.wrap(String.format("Hello %s!", aa).getBytes());

    }
}
