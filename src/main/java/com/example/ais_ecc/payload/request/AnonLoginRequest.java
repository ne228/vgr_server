package com.example.ais_ecc.payload.request;

import javax.validation.constraints.NotBlank;

public class AnonLoginRequest {
    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
