package com.rocketseat.nlw.passin.services;

import com.rocketseat.nlw.passin.domain.attendee.Attendee;
import com.rocketseat.nlw.passin.domain.checkin.CheckIn;
import com.rocketseat.nlw.passin.dto.attendee.AttendeeDetails;
import com.rocketseat.nlw.passin.dto.attendee.AttendeesListResponseDTO;
import com.rocketseat.nlw.passin.repositories.AttendeeRepository;
import com.rocketseat.nlw.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> findAllAttendeesByEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventAttendees(String eventId) {
        List<Attendee> attendeeList = this.findAllAttendeesByEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());

            LocalDateTime checkedInAt = checkIn.map(CheckIn::getCreatedAt).orElse(null);

            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }
}
