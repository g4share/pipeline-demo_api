// Copyright (c) 2024 g4share

package com.g4share.pipelineDemo.api.services;

import com.g4share.pipelineDemo.api.domain.User;
import com.g4share.pipelineDemo.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private final List<User> users = new ArrayList<>();

    @BeforeEach
    void init() {
        when(userRepository.findAll()).thenReturn(users);

        when(userRepository.save(any(User.class))).then((Answer<User>) invocation -> {
            User user = invocation.getArgument(0);
            users.add(user);
            return user;
        });
    }

    @Test
    void getAll() {
        assertThat(userService.getAll()).hasSize(0);
        userService.save(mock(User.class));
        assertThat(userService.getAll()).hasSize(1);

        System.out.println("------------ UT B");

    }
}