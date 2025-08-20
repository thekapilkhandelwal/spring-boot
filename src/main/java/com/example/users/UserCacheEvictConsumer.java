package com.example.users;

import java.util.Optional;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheEvictConsumer implements MessageListener {
    private final CacheManager cacheManager;
    private final RedisMessageListenerContainer container;

    @Value("${user.cache.evict.topic:user-cache-evict}")
    private String topic;

    @PostConstruct
    public void init() {
        container.addMessageListener(this, new ChannelTopic(topic));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String cacheName = new String(message.getBody());
        log.info("Evicting cache {} based on message", cacheName);
        Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear);
    }
}
