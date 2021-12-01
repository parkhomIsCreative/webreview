package com.example.webrewiew.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTTokenSuccesResponse {
    private boolean success;
    private String token;
}
