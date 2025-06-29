package com.oscarmartincol.med_booking_api.entity;

import jakarta.persistence.Entity;

@Entity
public class Patient extends User{
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
