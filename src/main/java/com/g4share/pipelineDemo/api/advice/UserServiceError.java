// Copyright (c) 2024 g4share
package com.g4share.pipelineDemo.api.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserServiceError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final List<ErrorDetails> errors = new ArrayList<>();

    private final String path;
}
