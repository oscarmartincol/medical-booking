package com.oscarmartincol.med_booking_api.repository;

import com.oscarmartincol.med_booking_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
