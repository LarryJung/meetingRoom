package com.larry.meetingroomreservation.web.controller.apiController;

import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/rooms")
@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("")
    public ResponseEntity<List<Room>> retrieveRoomsInfo() {
        List<Room> rooms = roomService.findAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("{id}")
    public ResponseEntity<Room> retrieveRoomInfo(@PathVariable Long id) {
        Room room = roomService.findById(id);
        return ResponseEntity.ok(room);
    }

}
