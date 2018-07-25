package com.larry.meetingroomreservation.web.controller.apiController;

import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.security.token.JwtPostAuthorizationToken;
import com.larry.meetingroomreservation.security.token.PostAuthorizationToken;
import com.larry.meetingroomreservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> retrieveUserInfo(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUsername(Authentication authentication) {
        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken)authentication;

        return token.getUserId();
    }
}
