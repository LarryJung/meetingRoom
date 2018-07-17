package com.larry.meetingroomreservation.service;

import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void iniDataForTesting() {
        User larry = User.builder()
                .userId("larry")
                .password("test")
                .name("jung")
                .email("larry@gmail.com")
                .roleName(RoleName.ADMIN)
                .build();
        User charry = User.builder()
                .userId("charry")
                .password("test")
                .name("chae")
                .roleName(RoleName.USER)
                .email("charry@gmail.com").build();

        userRepository.save(larry);
        userRepository.save(charry);
    }

}
