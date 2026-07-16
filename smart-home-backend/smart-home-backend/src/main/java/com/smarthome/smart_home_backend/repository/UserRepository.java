package com.smarthome.smart_home_backend.repository;

import com.smarthome.smart_home_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Long> {
}