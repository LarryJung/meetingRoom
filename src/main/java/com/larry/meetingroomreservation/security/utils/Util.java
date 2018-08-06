package com.larry.meetingroomreservation.security.utils;

import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

    public static List<SimpleGrantedAuthority> parseAuthorities(RoleName role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
    }

}
