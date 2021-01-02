package com.example.boba.service;

import com.auth0.jwt.JWT;
import com.example.boba.model.user.ApplicationUser;
import com.example.boba.model.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.boba.ConfigUtils.TOKEN_PREFIX;

@Service
public class GetUserInfoFromJwt {


    public ApplicationUser getUserFromJwt(String jwt) {
        jwt = jwt.replace(TOKEN_PREFIX, "");
        String subs = JWT.decode(jwt).getSubject();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(subs, ApplicationUser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
