package com.smarthome.smart_home_backend.repository;

import com.smarthome.smart_home_backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository
        extends JpaRepository<Room, Long> {

    List<Room> findByHome_Id(Long homeId);
}