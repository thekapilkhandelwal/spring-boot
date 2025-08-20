package com.example.users;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheEvictConsumer {
    private final CacheManager cacheManager;

    @KafkaListener(
            topics = "${user.cache.evict.topic:user-cache-evict}",
            groupId = "#{T(java.util.UUID).randomUUID().toString()}"
    )
    public void listen(String cacheName) {
        log.info("Evicting cache {} based on message", cacheName);
        Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear);
    }
}
