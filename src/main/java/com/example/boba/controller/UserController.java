package com.example.boba.controller;

import com.example.boba.model.user.ApplicationUser;
import com.example.boba.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostMapping("sign-up")
    public ApplicationUser signUp(@RequestBody ApplicationUser applicationUser) {
        if (userRepository.findOneByEmail(applicationUser.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Duplicated email");
        } else {
            applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
            return userRepository.save(applicationUser);
        }
    }
}
