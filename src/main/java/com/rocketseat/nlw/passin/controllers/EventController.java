package com.rocketseat.nlw.passin.controllers;

import com.rocketseat.nlw.passin.dto.event.EventIdDTO;
import com.rocketseat.nlw.passin.dto.event.EventRequestDTO;
import com.rocketseat.nlw.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventIdDTO> create(
            @RequestBody EventRequestDTO eventRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        EventIdDTO savedEvent = this.eventService.create(eventRequestDTO);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(savedEvent.eventId()).toUri();

        return ResponseEntity.created(uri).body(savedEvent);
    }
}
