package com.oscarmartincol.med_booking_api.entity;

import jakarta.persistence.Entity;

@Entity
public class Doctor extends User{

    private String fullname;
    private String specialty;



    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
