package com.example.users;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User create(User user) {
        log.info("Creating user {}", user.getName());
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        log.info("Deleting user with id {}", id);
        repository.deleteById(id);
    }

    public void deleteByName(String name) {
        log.info("Deleting user with name {}", name);
        repository.deleteByName(name);
    }

    public User updateSalary(Long id, Double salary) {
        return repository.findById(id).map(user -> {
            log.info("Updating salary for user {} to {}", id, salary);
            user.setSalary(salary);
            return repository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
