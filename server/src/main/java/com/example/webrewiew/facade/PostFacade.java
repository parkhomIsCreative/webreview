package com.example.webrewiew.facade;

import com.example.webrewiew.dto.PostDTO;
import com.example.webrewiew.entity.Post;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class PostFacade {

    MapperFactory factory = new DefaultMapperFactory.Builder().build();

    public PostDTO postToPostDTO(Post post){
        factory.classMap(Post.class,PostDTO.class);
        MapperFacade mapper = factory.getMapperFacade();
        return mapper.map(post,PostDTO.class);
    }


}
