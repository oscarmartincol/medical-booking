package com.oscarmartincol.med_booking_api.repository;

import com.oscarmartincol.med_booking_api.entity.Doctor;
import com.oscarmartincol.med_booking_api.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

       Optional<Doctor> findDoctorById(Long id);
}
