package com.example.webrewiew.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    private String username;
    private String title;
    private String caption;
    private String location;
    private Integer likes;
    private Set<String> likedUsers;

}
