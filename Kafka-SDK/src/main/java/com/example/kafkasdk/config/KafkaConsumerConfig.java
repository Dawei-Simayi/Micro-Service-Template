package com.example.kafkasdk.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;


@EnableKafka
@Slf4j
public class KafkaConsumerConfig {

    @Bean
    public RecordMessageConverter messageConverter() {
        return new StringJsonMessageConverter();
    }

    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload String message,
                       @Header(KafkaHeaders.PARTITION) int partition,
                       @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("Listen message:{}, partition:{}, offset: {}", message, partition, offset);
    }

    @KafkaHandler
    public void handleOtherMessage(OtherMessage message) {
        // handle other message types
        log.info("handle message:{}", message);
    }

    public static class OtherMessage {
        // define other message types
    }

}
