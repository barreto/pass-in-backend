package com.rocketseat.nlw.passin.services;

import com.rocketseat.nlw.passin.domain.attendee.Attendee;
import com.rocketseat.nlw.passin.domain.checkin.CheckIn;
import com.rocketseat.nlw.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import com.rocketseat.nlw.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckInExistence(attendee.getId());

        CheckIn checkIn = new CheckIn();
        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(checkIn);
    }

    private void verifyCheckInExistence(String attendeeId) {
        Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendeeId);

        if (checkIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in.");
    }

    public Optional<CheckIn> findByAttendeeId(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
