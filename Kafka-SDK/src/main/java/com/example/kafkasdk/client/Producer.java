package com.example.kafkasdk.client;

import com.alibaba.fastjson2.JSONObject;
import com.example.common.response.Response;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/kafka")
public class Producer {
    private static Logger logger = LoggerFactory.getLogger(Producer.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    @PostMapping("/message/send")
    public String produce(@RequestParam("topic") String topic,
                        @RequestParam("message") String message) {
        kafkaTemplate.send(topic, message);
        logger.info("send message:{} to topic:{}", message, topic);
        return "success";
    }

    @PostMapping("/topic/delete")
    public String deleteTopic(@RequestParam("topicName") String topicName) {
        AdminClient adminClient = getKafkaClient();
        DeleteTopicsResult result = adminClient.deleteTopics(Arrays.asList(topicName));
        return result.toString();
    }

    @GetMapping("/topic/list")
    public Response listTopics() throws ExecutionException, InterruptedException {
        AdminClient kafkaClient = getKafkaClient();
        ListTopicsResult topicsResult = kafkaClient.listTopics();
        KafkaFuture<Set<String>> names = topicsResult.names();
        String[] result = names.get().stream().toArray(String[]::new);
        String s = JSONObject.from(result).toString();
        return Response.success(s);
    }

    public AdminClient getKafkaClient() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootStrapServer);
        AdminClient adminClient = AdminClient.create(properties);
        return adminClient;
    }

}