package com.larry.meetingroomreservation.domain.repository;

import com.larry.meetingroomreservation.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long>{
}
