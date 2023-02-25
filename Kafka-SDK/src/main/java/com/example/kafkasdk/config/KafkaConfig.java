package com.example.kafkasdk.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    @Bean(name = "kafkaAdminClient")
    public AdminClient getKafkaClient() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootStrapServer);
        AdminClient adminClient = AdminClient.create(properties);
        return adminClient;
    }
}
