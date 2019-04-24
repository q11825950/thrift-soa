package com.example;

import com.github.mql.rpc.consumer.RpcConsumerConfiguration;
import com.xxce.service.dbserver.SyncRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.nio.ByteBuffer;

/**
 * Created by henry on 2018/7/23.
 */
@Slf4j
//@SpringBootApplication(scanBasePackages = {"com.example"})
@Import(RpcConsumerConfiguration.class)
public class Application {
    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        //传输层类型
        TTransport transport = null;
        try {
//            TSocket soket = new TSocket("172.16.238.3",9500);
            TSocket soket = new TSocket("192.168.1.138",9500);
            transport = new TFramedTransport(soket);

            TProtocol protocol = new TMultiplexedProtocol(new TBinaryProtocol(transport,2000,2000),"com.xxce.service.dbserver.SyncRequestService");
            //协议
//            TProtocol protocol = new TBinaryProtocol(transport);
            SyncRequestService.Client client = new SyncRequestService.Client(protocol);
            transport.open();
            ByteBuffer byteBuffer = client.SyncRequest(ByteBuffer.wrap(new byte[1023]), 1);
            System.out.println(new String(byteBuffer.array()));

        }  catch (Exception e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
    }
}
