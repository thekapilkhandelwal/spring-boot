package com.example.users;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheEvictProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CacheManager cacheManager;

    @Value("${user.cache.evict.topic:user-cache-evict}")
    private String topic;

    public void evictUsersCache() {
        Optional.ofNullable(cacheManager.getCache("users")).ifPresent(Cache::clear);
        log.info("Evicting users cache and notifying others");
        kafkaTemplate.send(topic, "users");
    }
}
