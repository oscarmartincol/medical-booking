package com.oscarmartincol.med_booking_api.repository;

import com.oscarmartincol.med_booking_api.entity.Appointment;
import com.oscarmartincol.med_booking_api.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient_Username(String username);
    List<Appointment> findByDoctor_Username(String username);

    boolean existsByDoctorAndDateTime(Doctor doctor, LocalDateTime dateTime);
}
