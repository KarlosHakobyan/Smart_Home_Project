package com.smarthome.smart_home_backend.service;

import com.smarthome.smart_home_backend.model.Home;
import com.smarthome.smart_home_backend.model.Room;
import com.smarthome.smart_home_backend.repository.HomeRepository;
import com.smarthome.smart_home_backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class EditService {

    private final HomeRepository homeRepository;
    private final RoomRepository roomRepository;

    public EditService(
            HomeRepository homeRepository,
            RoomRepository roomRepository
    ) {
        this.homeRepository = homeRepository;
        this.roomRepository = roomRepository;
    }

    /* =========================================================
       HOME EDIT
       ========================================================= */

    public Home updateHome(
            Long id,
            Home updatedHome
    ) {

        Home existingHome = homeRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Home not found"
                        )
                );

        existingHome.setName(
                updatedHome.getName()
        );

        existingHome.setAddress(
                updatedHome.getAddress()
        );

        existingHome.setTimezone(
                updatedHome.getTimezone()
        );

        return homeRepository.save(
                existingHome
        );
    }

    /* =========================================================
       ROOM EDIT
       ========================================================= */

    public Room updateRoom(
            Long id,
            Room updatedRoom
    ) {

        Room existingRoom = roomRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Room not found"
                        )
                );

        existingRoom.setName(
                updatedRoom.getName()
        );

        existingRoom.setFloor(
                updatedRoom.getFloor()
        );

        return roomRepository.save(
                existingRoom
        );
    }
}