package com.oscarmartincol.med_booking_api.service;

import com.oscarmartincol.med_booking_api.dto.AuthRequest;
import com.oscarmartincol.med_booking_api.entity.Patient;
import com.oscarmartincol.med_booking_api.repository.UserRepository;
import com.oscarmartincol.med_booking_api.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwt;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwt) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    public void register(AuthRequest request) {
        var user = new Patient();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_PATIENT");
        ((Patient) user).setFullName("Paciente demo");
        ((Patient) user).setDni("12345678");
        userRepository.save(user);
    }

    public String login(AuthRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return jwt.generateToken(user.getUsername(), user.getRole());
    }
}
