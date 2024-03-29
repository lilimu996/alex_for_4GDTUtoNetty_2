package com.rxkj;

import com.rxkj.controller.AlexForDTUServer;
import com.rxkj.mapper.DeviceList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * by alex
 * 2020年11月16日
 * springboot 启动类
 */
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class AlexfordtuApplication {

    public static void main(String[] args) {

        DeviceList.initDEVICE_VECTOR();

        // 获取application的上下文
        ApplicationContext applicationContext = SpringApplication.run(AlexfordtuApplication.class, args);

        /**
         * dtu服务端随springboot同步启动
         * 启动netty dtu的服务端
         */
        AlexForDTUServer server = applicationContext.getBean(AlexForDTUServer.class);
        server.start();
    }

}
