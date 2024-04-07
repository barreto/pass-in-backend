package com.rocketseat.nlw.passin.services;

import com.rocketseat.nlw.passin.domain.attendee.Attendee;
import com.rocketseat.nlw.passin.domain.attendee.exception.AttendeeAlreadySubscribedException;
import com.rocketseat.nlw.passin.domain.attendee.exception.AttendeeNotFoundException;
import com.rocketseat.nlw.passin.domain.checkin.CheckIn;
import com.rocketseat.nlw.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.rocketseat.nlw.passin.dto.attendee.AttendeeDetails;
import com.rocketseat.nlw.passin.dto.attendee.AttendeesListResponseDTO;
import com.rocketseat.nlw.passin.dto.attendee.BadgeDTO;
import com.rocketseat.nlw.passin.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> findAllAttendeesByEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventAttendees(String eventId) {
        List<Attendee> attendeeList = this.findAllAttendeesByEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.findByAttendeeId(attendee.getId());

            LocalDateTime checkedInAt = checkIn.map(CheckIn::getCreatedAt).orElse(null);

            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void save(Attendee attendee) {
        this.attendeeRepository.save(attendee);
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> attendee = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

        if (attendee.isPresent()) throw new AttendeeAlreadySubscribedException("Attendee is already registered.");
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = findAttendeeById(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri();

        BadgeDTO badgeDTO = new BadgeDTO(
                attendee.getName(),
                attendee.getEmail(),
                uri.toString(),
                attendee.getEvent().getId()
        );

        return new AttendeeBadgeResponseDTO(badgeDTO);
    }

    public void checkInAttendee(String attendeeId) {
        Attendee attendee = this.findAttendeeById(attendeeId);

        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee findAttendeeById(String attendeeId) {
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with id: " + attendeeId));
    }
}
