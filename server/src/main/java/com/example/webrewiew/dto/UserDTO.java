package com.example.webrewiew.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    private Long id;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;


    private String username;
    private String email;
    private String bio;


}
