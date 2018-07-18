package com.larry.meetingroomreservation.web.controller;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/reservations")
@RestController
public class ReservationController {

    private final Logger log = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/{reservedDate}/rooms/{roomId}")
    public ResponseEntity<List<Reservation>> retrieveReservation(@PathVariable String reservedDate, @PathVariable Long roomId) {
        List<Reservation> reservations = reservationService.findAllByDateAndRoom(reservedDate, roomId);
        log.info("found reservations : {}", reservations);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(reservations);
    }

    @PostMapping("")
    public ResponseEntity<Void> registerReservation(@RequestBody @Valid Reservation reservation) {
        log.info("register reservation : {}", reservation.toString());
        reservationService.reserve(reservation);
        return ResponseEntity.ok().build();
    }
}
