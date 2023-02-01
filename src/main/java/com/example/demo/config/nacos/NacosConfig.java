package com.example.demo.config.nacos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosConfig {
    @Value("Nacos.address")
    private String address;
    @Value("Nacos.port")
    private String port;
    @Value("Nacos.userName")
    private String userName;
    @Value("Nacos.password")
    private String password;
}
