package com.example.kafkasdk.client;

import com.alibaba.fastjson2.JSONObject;
import com.example.common.response.ResponseResult;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/kafka")
public class KafkaSender {
    private static Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    @Autowired
    @Qualifier("kafkaProducerClient")
    private Producer producer;
    @Autowired
    @Qualifier("kafkaAdminClient")
    private AdminClient adminClient;

    @PostMapping("/message/send")
    public String send(@RequestParam("topic") String topic,
                        @RequestParam("message") String message) {
        kafkaTemplate.send(topic, message);
        logger.info("send message:{} to topic:{}", message, topic);
        return "success";
    }

    @PostMapping("/message/produce")
    public String produce(@RequestParam("topic") String topic,
                          @RequestParam("message") String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    System.out.println("Error sending message");
                    e.printStackTrace();
                } else {
                    System.out.println("Message sent to partition " +
                            recordMetadata.partition() +
                            ", offset " + recordMetadata.offset());
                }
            }
        });
        return "success";
    }
    @PostMapping("/topic/delete")
    public ResponseResult deleteTopic(@RequestParam("topicName") String topicName) {
        DeleteTopicsResult result = adminClient.deleteTopics(Arrays.asList(topicName));
        return ResponseResult.success(result.toString());
    }

    @PostMapping("/topic/create")
    public ResponseResult createTopic(@RequestParam("topicName") String topicName) {
        NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
        CreateTopicsResult result = adminClient.createTopics(Arrays.asList(newTopic));
        return ResponseResult.success("create success");
    }
    @PostMapping("/topic/list")
    public ResponseResult listTopics() throws ExecutionException, InterruptedException {
        ListTopicsResult topicsResult = adminClient.listTopics();
        KafkaFuture<Set<String>> names = topicsResult.names();
        String[] result = names.get().stream().toArray(String[]::new);
        String s = JSONObject.toJSONString(result);
        return ResponseResult.success(s);
    }

}