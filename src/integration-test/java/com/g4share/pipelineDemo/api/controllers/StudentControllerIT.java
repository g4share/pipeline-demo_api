// Copyright (c) 2024 g4share

package com.g4share.pipelineDemo.api.controllers;

import com.g4share.pipelineDemo.api.Application;
import com.g4share.pipelineDemo.api.domain.User;
import com.g4share.pipelineDemo.api.repositories.UserRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class StudentControllerIT {

    @Inject
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void getAllUsers() {
        System.out.println("AAAAAAAAA---- " + port);
        assertThat(getUsers()).hasSize(0);
        System.out.println("------------ IT 1");
    }

    @Test
    public void addUser() {
        List<User> users = getUsers();

        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("mail@domain.com");

        User newUser = addUser(user);
        assertThat(newUser.getId()).isGreaterThan(0);

        List<User> newUsers = getUsers();
        assertThat(newUsers).hasSize(users.size() + 1);
        System.out.println("------------ IT 2");
    }

    private List<User> getUsers() {
        ResponseEntity<List<User>> response =
                restTemplate.exchange(createURLWithPort("/user/api/v1/users"),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() { });

        return response.getBody();
    }

    public User addUser(User user) {
            ResponseEntity<User> response = restTemplate.exchange(
                    createURLWithPort("/user/api/v1/users"),
                    HttpMethod.POST,
                    jsonEntity(user),
                    User.class);

            return response.getBody();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private <T> HttpEntity<T> jsonEntity(T entity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(entity, headers);
    }
}
