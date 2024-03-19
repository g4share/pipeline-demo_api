// Copyright (c) 2024 g4share
package com.g4share.pipelineDemo.api.services;

import com.g4share.pipelineDemo.api.domain.User;
import com.g4share.pipelineDemo.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public Optional<User> getById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User save(User user) {
       return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
