package com.rocketseat.nlw.passin.services;

import com.rocketseat.nlw.passin.domain.event.Event;
import com.rocketseat.nlw.passin.dto.event.EventIdDTO;
import com.rocketseat.nlw.passin.dto.event.EventRequestDTO;
import com.rocketseat.nlw.passin.repositories.AttendeeRepository;
import com.rocketseat.nlw.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;

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
}
