package com.larry.meetingroomreservation.web.controller;

import com.larry.meetingroomreservation.domain.entity.Reservation;
import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.service.ReservationService;
import com.larry.meetingroomreservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/reservations/rooms/{roomId}")
    public String showReservationsToday(@PathVariable Long roomId, Model model) {
        LocalDate today = LocalDate.of(2018, 7, 17);
        Room room = roomService.findById(roomId);
        model.addAttribute("day", today);
        model.addAttribute("room", room);
        return "reservation/show";
    }

}
