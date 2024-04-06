package com.rocketseat.nlw.passin.controllers;

import com.rocketseat.nlw.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.rocketseat.nlw.passin.services.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(
            @PathVariable String attendeeId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        AttendeeBadgeResponseDTO attendeeBadge = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);

        return ResponseEntity.ok(attendeeBadge);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<String> registerCheckIn(
            @PathVariable String attendeeId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        this.attendeeService.checkInAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }
}