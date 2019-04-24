package com.example.service;

import com.github.mql.rpc.provider.RpcService;
import com.xxce.service.dbserver.SyncRequestService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2019/4/23 0023.
 */
@Service
@RpcService(version="1.1.2", weight=3)
public class SyncRequestServiceImpl implements SyncRequestService.Iface {
    @Override
    public void ping() throws TException {

    }

    @Override
    public ByteBuffer SyncRequest(ByteBuffer reqmsg, int type) throws TException {
        String aa = null;
        try {
            aa = new  String(reqmsg.array(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(aa);

        return ByteBuffer.wrap(String.format("Hello %s!", aa).getBytes());
    }

    @Override
    public int SyncNotice(String reqmsg, int type) throws TException {
        return 0;
    }
}
