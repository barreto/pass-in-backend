package com.rocketseat.nlw.passin.dto.event;

import com.rocketseat.nlw.passin.domain.event.Event;
import lombok.Getter;

@Getter
public class EventResponseDTO {
    private final EventDetailDTO event;

    public EventResponseDTO(Event event, Integer numberOfAttendees) {
        this.event = new EventDetailDTO(
                event.getId(),
                event.getTitle(),
                event.getDetails(),
                event.getSlug(),
                event.getMaximumAttendees(),
                numberOfAttendees
        );
    }
}
