package com.larry.meetingroomreservation.domain.repository;

import com.larry.meetingroomreservation.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
