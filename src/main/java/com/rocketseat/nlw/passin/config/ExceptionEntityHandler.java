package com.rocketseat.nlw.passin.config;

import com.rocketseat.nlw.passin.domain.attendee.exception.AttendeeAlreadySubscribedException;
import com.rocketseat.nlw.passin.domain.attendee.exception.AttendeeNotFoundException;
import com.rocketseat.nlw.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import com.rocketseat.nlw.passin.domain.event.exception.EventNotFoundException;
import com.rocketseat.nlw.passin.domain.event.exception.FullEventException;
import com.rocketseat.nlw.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler({EventNotFoundException.class, AttendeeNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleNotFoundExceptions(RuntimeException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    @ExceptionHandler(FullEventException.class)
    public ResponseEntity<ErrorResponseDTO> handleFullEventException(FullEventException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(AttendeeAlreadySubscribedException.class)
    public ResponseEntity<String> handleAttendeeAlreadySubscribedException(AttendeeAlreadySubscribedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity<String> handleCheckInAlreadyExistsException(CheckInAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
