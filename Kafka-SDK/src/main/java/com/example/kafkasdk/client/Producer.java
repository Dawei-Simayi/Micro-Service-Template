package com.example.kafkasdk.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Producer {
    private static Logger logger = LoggerFactory.getLogger(Producer.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @PostMapping("/kafka/message/send")
    public String produce(@RequestParam("topic") String topic,
                        @RequestParam("message") String message) {
        kafkaTemplate.send(topic, message);
        logger.info("send message:{} to topic:{}", message, topic);
        return "success";
    }

}