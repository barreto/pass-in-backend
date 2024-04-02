package com.rocketseat.nlw.passin.repositories;

import com.rocketseat.nlw.passin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, String> {
}
