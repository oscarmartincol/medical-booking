package com.oscarmartincol.med_booking_api.entity;

import jakarta.persistence.Entity;

@Entity
public class Patient extends User{
    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
