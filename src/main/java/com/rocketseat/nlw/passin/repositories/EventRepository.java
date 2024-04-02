package com.rocketseat.nlw.passin.repositories;

import com.rocketseat.nlw.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
