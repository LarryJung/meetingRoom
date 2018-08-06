package com.larry.meetingroomreservation.service;

import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(EntityNotFoundException::new);
    }
}
