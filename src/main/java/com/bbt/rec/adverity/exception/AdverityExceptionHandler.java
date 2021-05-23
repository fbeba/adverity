package com.bbt.rec.adverity.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AdverityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidDimensionTypeException.class)
    protected ResponseEntity<Object> handleInvalidDimension(RuntimeException ex, WebRequest request) {
        String body = "Invalid dimension specified.";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = RepositoryLockedException.class)
    protected ResponseEntity<Object> handleLockedRepo(RuntimeException ex, WebRequest request) {
        String body = "Repository currently not available.";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }
}
