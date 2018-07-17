package com.larry.meetingroomreservation.service;

import com.larry.meetingroomreservation.domain.entity.Room;
import com.larry.meetingroomreservation.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @PostConstruct
    private void iniDataForTesting() {

        Room room101 = Room.builder()
                .roomName("101호")
                .occupancy(5).build();

        Room room102 = Room.builder()
                .roomName("102호")
                .occupancy(5).build();

        Room room103 = Room.builder()
                .roomName("103호")
                .occupancy(3).build();

        Room room104 = Room.builder()
                .roomName("104호")
                .occupancy(3).build();

        Room room105 = Room.builder()
                .roomName("105호")
                .occupancy(3).build();

        roomRepository.save(room101);
        roomRepository.save(room102);
        roomRepository.save(room103);
        roomRepository.save(room104);
        roomRepository.save(room105);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }
}
