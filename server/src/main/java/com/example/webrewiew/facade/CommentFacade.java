package com.example.webrewiew.facade;

import com.example.webrewiew.dto.CommentDTO;
import com.example.webrewiew.entity.Comment;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class CommentFacade {

    MapperFactory factory = new DefaultMapperFactory.Builder().build();

    public CommentDTO commentToCommentDTO(Comment comment){
        factory.classMap(Comment.class, CommentDTO.class);
        MapperFacade mapper = factory.getMapperFacade();
        return mapper.map(comment, CommentDTO.class);
    }

}
