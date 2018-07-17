package com.larry.meetingroomreservation.domain.repository;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
}
