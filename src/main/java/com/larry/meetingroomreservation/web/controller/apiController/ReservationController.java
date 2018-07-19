package com.larry.meetingroomreservation.web.controller.apiController;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.dto.ReservationDto;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.service.ReservationService;
import com.larry.meetingroomreservation.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/reservations")
@RestController
public class ReservationController {

    private final Logger log = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/{reservedDate}/rooms/{roomId}")
    public ResponseEntity<List<Reservation>> retrieveReservation(@PathVariable String reservedDate, @PathVariable Long roomId) {
        List<Reservation> reservations = reservationService.findAllByDateAndRoom(reservedDate, roomId);
        log.info("found reservations : {}", reservations);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(reservations);
    }

    @PostMapping("/{reservedDate}/rooms/{roomId}")
    public ResponseEntity<Void> registerReservation(@RequestBody @Valid ReservationDto target, @PathVariable Long roomId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("valid error : {}", bindingResult.toString());
        }
        User loginUser = new User("larry", "test", "jung", "larry@gmail.com", RoleName.ADMIN);
        Room room = roomService.findById(roomId);
        Reservation reservation = target.toEntity();
        reservation.bookBy(loginUser);
        reservation.bookRoom(room);
        log.info("register reservation : {}", reservation);
        reservationService.reserve(reservation);
        return ResponseEntity.ok().build();
    }
}
