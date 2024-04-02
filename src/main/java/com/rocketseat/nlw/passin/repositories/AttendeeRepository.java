package com.rocketseat.nlw.passin.repositories;

import com.rocketseat.nlw.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, String> {
}
