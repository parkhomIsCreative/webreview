package com.example.webrewiew.service;

import com.example.webrewiew.entity.User;
import com.example.webrewiew.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;

public class GetUserByPrincipal {

    public static User get(UserRepository userRepository, Principal principal)
    {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }
}
