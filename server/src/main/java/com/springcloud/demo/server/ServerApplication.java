package com.springcloud.demo.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {"com.springcloud.demo.server"}, exclude = {GsonAutoConfiguration.class})
@MapperScan("com.springcloud.demo.server.data.dao")
@ConfigurationPropertiesScan(basePackages = {"com.springcloud.demo.server.config"})
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
