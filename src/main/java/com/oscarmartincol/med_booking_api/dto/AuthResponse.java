package com.oscarmartincol.med_booking_api.dto;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
