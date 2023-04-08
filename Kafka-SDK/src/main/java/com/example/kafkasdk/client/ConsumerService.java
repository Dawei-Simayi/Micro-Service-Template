package com.example.kafkasdk.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {
    private static final String TOPIC_NAME = "test";

    @KafkaListener(groupId = "test-group", topics = TOPIC_NAME)
    public void receive(ConsumerRecord<String, String> record) {
        log.info("Received message: key = " + record.key() +
                ", value = " + record.value() +
                ", partition = " + record.partition());
    }
}