package com.larry.meetingroomreservation.web.controller;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.service.ReservationService;
import com.larry.meetingroomreservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomService roomService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

//    @RequestMapping("/show")
//    public String show() {
//        return "reservation/show";
//    }

    @GetMapping("/reservations/{reservedDate}/rooms/{roomId}")
    public String showReservationsToday(@PathVariable String reservedDate, @PathVariable Long roomId, Model model) {
        LocalDate date = LocalDate.parse(reservedDate);
        Room room = roomService.findById(roomId);
        model.addAttribute("day", date);
        model.addAttribute("room", room);
        return "reservation/show";
    }

}
