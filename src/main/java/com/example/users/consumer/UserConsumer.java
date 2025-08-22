package com.example.users.consumer;

import com.example.users.model.User;
import com.example.users.producer.UserCacheEvictProducer;
import com.example.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer implements MessageListener {
    private final UserRepository repository;
    private final UserCacheEvictProducer cacheEvictProducer;
    private final RedisMessageListenerContainer container;
    private final ObjectMapper objectMapper;

    @Value("${user.topic.name:users}")
    private String topic;

    @PostConstruct
    public void init() {
        container.addMessageListener(this, new ChannelTopic(topic));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            User user = objectMapper.readValue(message.getBody(), User.class);
            log.info("Persisting user {} from redis", user.getName());
            repository.save(user);
            cacheEvictProducer.evictUsersCache();
        } catch (Exception e) {
            log.error("Failed to process message", e);
        }
    }
}
