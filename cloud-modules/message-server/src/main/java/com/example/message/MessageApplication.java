package com.example.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/** 如果使用注测中心则打开 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.example.message"}, exclude = {GsonAutoConfiguration.class})
@MapperScan("com.example.message.data.dao")
@ConfigurationPropertiesScan(basePackages = {"com.example.message.config"})
public class MessageApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(MessageApplication.class, args);
    }
}
