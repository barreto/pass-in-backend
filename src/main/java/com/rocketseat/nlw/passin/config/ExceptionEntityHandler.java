package com.rocketseat.nlw.passin.config;

import com.rocketseat.nlw.passin.domain.event.exception.EventNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<String> handleEventNotFoundException(EventNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
