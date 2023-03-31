package com.web.iago.liga.controller;

import com.web.iago.liga.model.User;
import com.web.iago.liga.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class UserController {

    private final UserRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> all() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> find(@PathVariable Long id) {
        return customerRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User store(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return customerRepository.save(user);
    }

    @DeleteMapping
    public Boolean delete(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return true;
    }
}
