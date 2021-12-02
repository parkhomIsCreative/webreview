package com.example.webrewiew.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Indexed;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity
@Getter
@Setter
@Indexed

public  class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String postgroup;
    private String tag;

    @Column(columnDefinition = "text",
            length = 3000)
    private String review;

    private Integer likes;
    private Integer rating;

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> usersLiked = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String username;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createDate;



    @PrePersist
    protected void onCreate()
    {
        this.createDate = LocalDateTime.now();
    }

}
