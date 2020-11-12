package com.example.boba.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.boba.model.user.ApplicationUser;
import com.example.boba.model.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.boba.ConfigUtils.EXPIRATION_TIME;
import static com.example.boba.ConfigUtils.SECRET;

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

    @PostMapping("sign-in")
    public Map<String, Object> login(@RequestBody ApplicationUser applicationUser) throws JsonProcessingException {
        ApplicationUser user = userRepository.findOneByEmail(applicationUser.getEmail());
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        else {
            boolean passwordMatch = passwordEncoder.matches(applicationUser.getPassword(), user.getPassword());
            if (!passwordMatch) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            else {
                HashMap<String, Object> response = new HashMap<>();
                ObjectMapper objectMapper = new ObjectMapper();
                String subject = objectMapper.writeValueAsString(user);
                String token = JWT.create().withSubject(subject)
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(Algorithm.HMAC512(SECRET.getBytes()));

                response.put("userInfo", user);
                response.put("token", token);
                return response;
            }
        }

    }
}
