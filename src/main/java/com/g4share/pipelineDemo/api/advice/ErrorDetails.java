// Copyright (c) 2024 g4share
package com.g4share.pipelineDemo.api.advice;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ErrorDetails(@JsonInclude(JsonInclude.Include.NON_NULL) String field, String message) {
}
