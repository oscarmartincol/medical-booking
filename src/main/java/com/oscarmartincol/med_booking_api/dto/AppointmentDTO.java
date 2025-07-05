package com.oscarmartincol.med_booking_api.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {
    public Long doctorId;
    public LocalDateTime dateTime;
    public String reason;
}
