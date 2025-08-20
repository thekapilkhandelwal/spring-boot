package com.example.users;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserProducer producer;
    private final UserCacheEvictProducer cacheEvictProducer;

    public void create(User user) {
        log.info("Queuing creation of user {}", user.getName());
        producer.sendUser(user);
        cacheEvictProducer.evictUsersCache();
    }

    @Cacheable("users")
    public List<User> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        log.info("Deleting user with id {}", id);
        repository.deleteById(id);
        cacheEvictProducer.evictUsersCache();
    }

    public void deleteByName(String name) {
        log.info("Deleting user with name {}", name);
        repository.deleteByName(name);
        cacheEvictProducer.evictUsersCache();
    }

    public User updateSalary(Long id, Double salary) {
        return repository.findById(id).map(user -> {
            log.info("Updating salary for user {} to {}", id, salary);
            user.setSalary(salary);
            User saved = repository.save(user);
            cacheEvictProducer.evictUsersCache();
            return saved;
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
