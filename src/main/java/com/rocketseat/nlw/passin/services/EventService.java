package com.rocketseat.nlw.passin.services;

import com.rocketseat.nlw.passin.domain.attendee.Attendee;
import com.rocketseat.nlw.passin.domain.event.Event;
import com.rocketseat.nlw.passin.domain.event.exception.EventNotFoundException;
import com.rocketseat.nlw.passin.domain.event.exception.FullEventException;
import com.rocketseat.nlw.passin.dto.attendee.AttendeeIdDTO;
import com.rocketseat.nlw.passin.dto.attendee.AttendeeRequestDTO;
import com.rocketseat.nlw.passin.dto.event.EventIdDTO;
import com.rocketseat.nlw.passin.dto.event.EventRequestDTO;
import com.rocketseat.nlw.passin.dto.event.EventResponseDTO;
import com.rocketseat.nlw.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventIdDTO create(EventRequestDTO eventRequestDTO) {
        Event event = new Event();

        event.setTitle(eventRequestDTO.title());
        event.setDetails(eventRequestDTO.details());
        event.setMaximumAttendees(eventRequestDTO.maximumAttendees());
        event.setSlug(this.createSlug(eventRequestDTO.title()));

        this.eventRepository.save(event);

        return new EventIdDTO(event.getId());
    }

    private String createSlug(String text) {
        /* When applying the Normalizer.Form.NFD normalization to the word “São Paulo”, the accented characters
        are decomposed into their basic components. Therefore, “São Paulo” would become “São Paulo”. Note that the
        difference may not be visible, but internally, the character “ã” is represented as two characters: “a” and
        the tilde “~”. This is useful for certain text processing operations, such as sorting and searching. */
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

        return normalized
                .replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "") // Remove accentuation
                .replaceAll("[^\\w\\s]", "") // Remove non-textual characters
                .replaceAll("\\s+", "-") // Replace one or more white space(s) with hyphen
                .toLowerCase();
    }

    public EventResponseDTO getDetails(String eventId) {
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.findAllAttendeesByEvent(eventId);

        return new EventResponseDTO(event, attendeeList.size());
    }

    public AttendeeIdDTO subscribeAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

        Event event = getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.findAllAttendeesByEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) throw new FullEventException("The event is full.");

        Attendee attendee = new Attendee();
        attendee.setName(attendeeRequestDTO.name());
        attendee.setEmail(attendeeRequestDTO.email());
        attendee.setEvent(event);
        attendee.setCreatedAt(LocalDateTime.now());

        this.attendeeService.save(attendee);

        return new AttendeeIdDTO(attendee.getId());
    }

    private Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }
}
