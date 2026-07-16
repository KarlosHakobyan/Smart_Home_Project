package com.smarthome.smart_home_backend.repository;

import com.smarthome.smart_home_backend.model.UserHome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHomeRepository
        extends JpaRepository<UserHome, Long> {

    List<UserHome> findByUserId(Long userId);

    List<UserHome> findByHomeId(Long homeId);
}