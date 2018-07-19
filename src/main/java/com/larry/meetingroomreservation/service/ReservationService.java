package com.larry.meetingroomreservation.service;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.exceptions.AlreadyReservedException;
import com.larry.meetingroomreservation.domain.exceptions.ExcessAttendeeExceptioon;
import com.larry.meetingroomreservation.domain.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final Logger log = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findAllByDateAndRoom(String reservedDate, Long roomId) {
        LocalDate localDate = LocalDate.parse(reservedDate);
        log.info("local date : {}", localDate);
        return reservationRepository.findAllByReservedDateAndReservedRoomId(localDate, roomId);
    }

    public Reservation reserve(Reservation reservation) {
        if (isReservable(reservation)) {
            return reservationRepository.save(reservation);
        }
        throw new RuntimeException("예약할 수 없습니다.");
    }

    private boolean isReservable(Reservation reservation) {
        List<Reservation> reservations = reservationRepository.findAllByReservedDateAndReservedRoomId(
                reservation.getReservedDate(), reservation.getReservedRoom().getId());
        if (reservations.isEmpty()) {
            return true;
        }
        if(!reservation.isPossibleAttendeeNumber()) {
            throw new ExcessAttendeeExceptioon(String.format("인원 초과입니다. 허용인원 : %d", reservation.getReservedRoom().getOccupancy()));
        }
        return reservations.stream().noneMatch(r -> r.isOverlap(reservation));
    }
}
