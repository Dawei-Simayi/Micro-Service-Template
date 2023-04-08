package com.example.kafkasdk.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaAdminConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean(name = "kafkaAdminClient")
    public AdminClient getKafkaClient() {
        Map<String, Object> stringObjectMap = kafkaProperties.getAdmin().buildProperties();
        stringObjectMap.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
        AdminClient adminClient = AdminClient.create(stringObjectMap);
        return adminClient;
    }
}
