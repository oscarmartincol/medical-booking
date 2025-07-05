package com.oscarmartincol.med_booking_api.service;

import com.oscarmartincol.med_booking_api.dto.AppointmentDTO;
import com.oscarmartincol.med_booking_api.entity.Appointment;
import com.oscarmartincol.med_booking_api.entity.Patient;
import com.oscarmartincol.med_booking_api.entity.Doctor;
import com.oscarmartincol.med_booking_api.repository.AppointmentRepository;
import com.oscarmartincol.med_booking_api.repository.DoctorRepository;
import com.oscarmartincol.med_booking_api.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /* New appointment */
    public void create(AppointmentDTO appointmentDTO, String username) {

        Patient patient = patientRepository.findPatientByUsername(username)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Doctor doctor = doctorRepository.findDoctorById(appointmentDTO.doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        if (appointmentRepository.existsByDoctorAndDateTime(doctor, appointmentDTO.dateTime)) {
            throw new RuntimeException("El doctor ya tiene una cita asignada en este horario");
        }
        Appointment app = new Appointment();
        app.setPatient(patient);
        app.setDoctor(doctor);
        app.setDateTime(appointmentDTO.dateTime);
        app.setReason(appointmentDTO.reason);
        appointmentRepository.save(app);
    }

    /* Appointments for doctor*/
    public List<Appointment> getForPatient(String username) {
        return appointmentRepository.findByPatient_Username(username);
    }

    /* Appointments for patient*/
    public List<Appointment> getForDoctor(String username) {
        return appointmentRepository.findByDoctor_Username(username);
    }

    /* Delete appointment*/
    public void delete(Long id, String username) {
        Appointment app = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        if (!app.getPatient().getUsername().equals(username)) {
            throw new RuntimeException("No puedes cancelar esta cita");
        }
        appointmentRepository.delete(app);
    }
}
