package com.example.users.repository;

import com.example.users.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByName(String name);
    Optional<User> findByName(String name);
}
