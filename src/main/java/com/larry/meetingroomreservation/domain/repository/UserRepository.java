package com.larry.meetingroomreservation.domain.repository;

import com.larry.meetingroomreservation.domain.entity.SocialProviders;
import com.larry.meetingroomreservation.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserId(String userId);
    Optional<User> findBySocialIdAndSocialProvider(String socialId, SocialProviders provider);
}
