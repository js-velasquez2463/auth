package com.upc.auth;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String username;
    private String password;

    // Constructores, getters y setters
    public AuthenticationRequest() {}

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
