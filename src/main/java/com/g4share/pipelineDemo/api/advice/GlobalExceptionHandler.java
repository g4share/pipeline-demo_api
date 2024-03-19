// Copyright (c) 2024 g4share
package com.g4share.pipelineDemo.api.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Comparator;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                          HttpServletRequest request){
        UserServiceError errorResponse = new UserServiceError(request.getRequestURI());
        if (exception.getBindingResult().hasErrors()) {
            errorResponse.getErrors().addAll(exception.getBindingResult().getFieldErrors().stream()
                    .sorted(Comparator.comparing(FieldError::getField))
                    .map(error -> new ErrorDetails(error.getField(), error.getDefaultMessage()))
                    .toList());
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex,
                                             HttpServletRequest request) {
        UserServiceError errorResponse = new UserServiceError(request.getRequestURI());
        errorResponse.getErrors().add(new ErrorDetails(null, ex.getMessage()));

        return ResponseEntity.status(HttpStatus.valueOf(springStatus(ex).value())).body(errorResponse);
    }

    private HttpStatusCode springStatus(Exception e) {
        if (e instanceof HttpRequestMethodNotSupportedException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof HttpMediaTypeNotSupportedException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof HttpMediaTypeNotAcceptableException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof MissingPathVariableException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof MissingServletRequestParameterException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof MissingServletRequestPartException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof ServletRequestBindingException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof MethodArgumentNotValidException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof HandlerMethodValidationException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof NoHandlerFoundException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof NoResourceFoundException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof AsyncRequestTimeoutException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof ErrorResponseException ex) {
            return ex.getStatusCode();
        }
        if (e instanceof MaxUploadSizeExceededException ex) {
            return ex.getStatusCode();
        } else {
            if (e instanceof ConversionNotSupportedException) {
                return INTERNAL_SERVER_ERROR;
            }
            if (e instanceof TypeMismatchException) {
                return BAD_REQUEST;
            }
            if (e instanceof HttpMessageNotReadableException) {
                return BAD_REQUEST;
            }
            if (e instanceof HttpMessageNotWritableException) {
                return INTERNAL_SERVER_ERROR;
            }
            if (e instanceof MethodValidationException) {
                return INTERNAL_SERVER_ERROR;
            }
            if (e instanceof BindException) {
                return BAD_REQUEST;
            }
        }

        return INTERNAL_SERVER_ERROR;
    }
}
