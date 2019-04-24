package com.example;

import com.github.mql.rpc.provider.RpcServerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by henry on 2018/7/23.
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.example"})
@Import({RpcServerConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        /*try {
            TProcessor tprocessor = new HelloService.Processor<>(new HelloServiceImpl());
            TServerSocket serverTransport = new TServerSocket(9898);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
            //log.info("服务端开启....");
        } catch (TTransportException e) {
            //log.error("服务端开启异常.", e);
        }*/
    }
}
