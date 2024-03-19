// Copyright (c) 2024 g4share
package com.g4share.pipelineDemo.api.services;

import com.g4share.pipelineDemo.api.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    Optional<User> getById(Long userId);

    User save(User user);

    void delete(Long id);
}
