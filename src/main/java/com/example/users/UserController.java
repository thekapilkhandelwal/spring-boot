package com.example.users;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody User user) {
        log.info("Received request to create user {}", user.getName());
        service.create(user);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public List<User> list() {
        log.info("Listing all users");
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable String name) {
        service.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/salary")
    public User updateSalary(@PathVariable Long id, @RequestParam Double salary) {
        return service.updateSalary(id, salary);
    }
}
