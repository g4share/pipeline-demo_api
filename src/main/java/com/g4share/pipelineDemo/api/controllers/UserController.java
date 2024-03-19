// Copyright (c) 2024 g4share
package com.g4share.pipelineDemo.api.controllers;

import com.g4share.pipelineDemo.api.domain.User;
import com.g4share.pipelineDemo.api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResponseStatusException {
        User user = userService
                .getById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable(value = "id") Long userId,
                                               @Valid @RequestBody User userDetails) throws ResponseStatusException {
        User user = userService
                .getById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found for this id :: " + userId));

        user.setEmail(userDetails.getEmail());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());

        final User updatedEmployee = userService.save(user);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResponseStatusException {
        User user = userService
                .getById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found for this id :: " + userId));

        userService.delete(user.getId());

        Map <String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
