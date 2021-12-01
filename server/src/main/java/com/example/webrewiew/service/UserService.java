package com.example.webrewiew.service;

import com.example.webrewiew.dto.UserDTO;
import com.example.webrewiew.entity.User;
import com.example.webrewiew.entity.enums.ERole;
import com.example.webrewiew.exceptions.UserExistException;
import com.example.webrewiew.payload.request.SignupRequest;
import com.example.webrewiew.repository.UserRepository;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    MapperFactory factory = new DefaultMapperFactory.Builder().build();

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest in)
    {
        User user = new User();
        user.setEmail(in.getEmail());
        user.setFirstname(in.getFirstname());
        user.setLastname(in.getLastname());
        user.setUsername(in.getUsername());
        user.setPassword(passwordEncoder.encode(in.getPassword()));
        user.getRole().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving user {}", in.getUsername());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + "already exist.");
        }

    }

    public User updateUser (UserDTO userDTO, Principal principal)
    {
        User user = GetUserByPrincipal.get(userRepository, principal);
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());


        return userRepository.save(user);
    }

    public User getUserById(Long userId){
        return  userRepository.findUserById(userId)
                .orElseThrow(() -> new UserExistException("User not found"));
    }

    public User getCurrentUser(Principal principal)
    {
        return GetUserByPrincipal.get(userRepository, principal);
    }
}
