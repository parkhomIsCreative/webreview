package com.example.webrewiew.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    private String username;
    private String title;
    private String postgroup;
    private String tag;
    private String review;
    private Integer likes;
    private Integer rating;
    private Set<String> usersLiked;

}
