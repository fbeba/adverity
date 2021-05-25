package com.bbt.rec.adverity.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AdverityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidDimensionTypeException.class)
    protected ResponseEntity<Object> handleInvalidDimension(RuntimeException ex, WebRequest request) {
        String body = "Invalid dimension specified.";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request) {
        String body = "Invalid parameter specified.";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = RepositoryLockedException.class)
    protected ResponseEntity<Object> handleLockedRepo(RuntimeException ex, WebRequest request) {
        String body = "Repository is currently not available.";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(value = MultipartException.class)
    protected ResponseEntity<Object> handleMultipart(RuntimeException ex, WebRequest request) {
        String body = ex.getMessage();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
