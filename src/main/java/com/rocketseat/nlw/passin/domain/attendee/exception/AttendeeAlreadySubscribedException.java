package com.rocketseat.nlw.passin.domain.attendee.exception;

public class AttendeeAlreadySubscribedException extends RuntimeException {
    public AttendeeAlreadySubscribedException(String message) {
        super(message);
    }
}
