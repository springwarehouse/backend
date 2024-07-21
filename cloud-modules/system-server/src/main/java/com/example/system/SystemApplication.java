package com.example.system;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/** 如果使用注测中心则打开 */
@EnableDiscoveryClient
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"com.example.system.config"})
public class SystemApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SystemApplication.class, args);
    }
}
