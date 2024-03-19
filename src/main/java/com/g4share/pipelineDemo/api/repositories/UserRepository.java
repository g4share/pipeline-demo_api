// Copyright (c) 2024 g4share
package com.g4share.pipelineDemo.api.repositories;

import com.g4share.pipelineDemo.api.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}