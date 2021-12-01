package com.example.webrewiew.facade;

import com.example.webrewiew.dto.UserDTO;
import com.example.webrewiew.entity.User;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(User user){
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        factory.classMap(User.class, UserDTO.class);
        MapperFacade mapper = factory.getMapperFacade();

        return mapper.map(user,UserDTO.class);
    }



}
