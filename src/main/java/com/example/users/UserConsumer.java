package com.example.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer {
    private final UserRepository repository;
    private final UserCacheEvictProducer cacheEvictProducer;

    @KafkaListener(topics = "${user.topic.name:users}", groupId = "users")
    public void listen(User user) {
        log.info("Persisting user {} from kafka", user.getName());
        repository.save(user);
        cacheEvictProducer.evictUsersCache();
    }
}
