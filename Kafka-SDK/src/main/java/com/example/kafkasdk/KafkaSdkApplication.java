package com.example.kafkasdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaSdkApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaSdkApplication.class, args);
    }

}
