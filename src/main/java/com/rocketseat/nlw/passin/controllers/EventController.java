package com.rocketseat.nlw.passin.controllers;

import com.rocketseat.nlw.passin.dto.attendee.AttendeeIdDTO;
import com.rocketseat.nlw.passin.dto.attendee.AttendeeRequestDTO;
import com.rocketseat.nlw.passin.dto.attendee.AttendeesListResponseDTO;
import com.rocketseat.nlw.passin.dto.event.EventIdDTO;
import com.rocketseat.nlw.passin.dto.event.EventRequestDTO;
import com.rocketseat.nlw.passin.dto.event.EventResponseDTO;
import com.rocketseat.nlw.passin.services.AttendeeService;
import com.rocketseat.nlw.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    @PostMapping
    public ResponseEntity<EventIdDTO> create(
            @RequestBody EventRequestDTO eventRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        EventIdDTO savedEvent = this.eventService.create(eventRequestDTO);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(savedEvent.eventId()).toUri();

        return ResponseEntity.created(uri).body(savedEvent);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getDetails(@PathVariable String eventId) {
        EventResponseDTO event = eventService.getDetails(eventId);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String eventId) {
        AttendeesListResponseDTO attendeesListResponseDTO = this.attendeeService.getEventAttendees(eventId);
        return ResponseEntity.ok(attendeesListResponseDTO);
    }

    @PostMapping("/{eventId}/attendee")
    public ResponseEntity<AttendeeIdDTO> subscribeAttendeeOnEvent(
            @PathVariable String eventId,
            @RequestBody AttendeeRequestDTO attendeeRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        AttendeeIdDTO attendeeIdDTO = this.eventService.subscribeAttendeeOnEvent(eventId, attendeeRequestDTO);

        var uri = uriComponentsBuilder
                .path("/attendees/{attendeeId}/badge")
                .buildAndExpand(attendeeIdDTO.attendeeId())
                .toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }
}
