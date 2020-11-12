package com.example.boba.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.boba.model.user.ApplicationUser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.boba.ConfigUtils.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ApplicationUser creds = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), creds.getAuthorities()));

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        String subject = objectMapper.writeValueAsString(authResult.getPrincipal());
        String token = JWT.create().withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(Algorithm.HMAC512(SECRET.getBytes()));
        response.setHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + token);
    }
}
