package com.oscarmartincol.med_booking_api.controller;

import com.oscarmartincol.med_booking_api.dto.AppointmentDTO;
import com.oscarmartincol.med_booking_api.entity.Appointment;
import com.oscarmartincol.med_booking_api.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /* Appointments for patient */
    @GetMapping("/patient")
    public List<Appointment> getForPatient(@AuthenticationPrincipal UserDetails user) {
        return appointmentService.getForPatient(user.getUsername());
    }

    @GetMapping("/doctor")
    public List<Appointment> getForDoctor(@AuthenticationPrincipal UserDetails user) {
        return appointmentService.getForDoctor(user.getUsername());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentDTO dto, @AuthenticationPrincipal UserDetails user) {
        appointmentService.create(dto, user.getUsername());
        return  ResponseEntity.ok("Cita registrada con éxito");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        appointmentService.delete(id, user.getUsername());
        return  ResponseEntity.ok("Cita cancelada");
    }
}
