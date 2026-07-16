package com.smarthome.smart_home_backend.repository;

import com.smarthome.smart_home_backend.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository
        extends JpaRepository<Device, Long> {

    List<Device> findByRoom_Id(Long roomId);
}