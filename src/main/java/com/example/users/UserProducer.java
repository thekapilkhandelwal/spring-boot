package com.example.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProducer {
    private final KafkaTemplate<String, User> kafkaTemplate;

    @Value("${user.topic.name:users}")
    private String topic;

    public void sendUser(User user) {
        log.info("Sending user {} to topic {}", user.getName(), topic);
        kafkaTemplate.send(topic, user);
    }
}
