package com.larry.meetingroomreservation.domain.repository;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

    List<Reservation> findAllByReservedDateAndReservedRoomId(LocalDate localDate, Long roomId);

}
