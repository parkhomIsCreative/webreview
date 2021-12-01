package com.example.webrewiew.payload.request;


import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignupRequest {

    @Email(message = "Enter email in correct format")
    @NotBlank(message = "Email is required")

    private String email;

    @NotEmpty(message = "Please enter your Firstname")
    private String firstname;

    @NotEmpty(message = "Please enter your Lastname")
    private String lastname;

    @NotEmpty(message = "Please enter your Username")
    private String username;

    @NotEmpty(message = "Please enter your Password")
    @Size(min = 6)
    private String password;

    @NotEmpty(message = "Please confirm Password")
    @Size(min = 6)
    private String confirmPassword;

}
