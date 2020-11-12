package com.example.boba.service;

import com.example.boba.model.user.ApplicationUser;
import com.example.boba.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Collections.emptyList;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findOneByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        } else return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }
}
