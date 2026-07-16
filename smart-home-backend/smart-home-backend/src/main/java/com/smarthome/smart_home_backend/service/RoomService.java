package com.smarthome.smart_home_backend.service;

import com.smarthome.smart_home_backend.model.Home;
import com.smarthome.smart_home_backend.model.Room;
import com.smarthome.smart_home_backend.repository.HomeRepository;
import com.smarthome.smart_home_backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepo;
    private final HomeRepository homeRepo;

    public RoomService(
            RoomRepository roomRepo,
            HomeRepository homeRepo
    ) {
        this.roomRepo = roomRepo;
        this.homeRepo = homeRepo;
    }

    /* =========================================================
       GET ALL
       ========================================================= */

    public List<Room> getAllRooms() {

        return roomRepo.findAll();
    }

    /* =========================================================
       GET BY ID
       ========================================================= */

    public Room getRoomById(Long id) {

        return roomRepo.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Room not found with id: " + id
                        )
                );
    }

    /* =========================================================
       GET BY HOME
       ========================================================= */

    public List<Room> getRoomsByHome(Long homeId) {

        return roomRepo.findByHome_Id(homeId);
    }

    /* =========================================================
       CREATE
       ========================================================= */

    public Room createRoom(Room room) {

        if (
                room.getHome() == null ||
                        room.getHome().getId() == null
        ) {

            throw new RuntimeException(
                    "Home id is required"
            );
        }

        Long homeId = room.getHome().getId();

        Home home = homeRepo.findById(homeId)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Home not found with id: " + homeId
                        )
                );

        room.setHome(home);

        return roomRepo.save(room);
    }

    /* =========================================================
       UPDATE
       ========================================================= */

    public Room updateRoom(
            Long id,
            Room updated
    ) {

        Room room = roomRepo.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Room not found with id: " + id
                        )
                );

        room.setName(updated.getName());
        room.setFloor(updated.getFloor());

        return roomRepo.save(room);
    }

    /* =========================================================
       DELETE
       ========================================================= */

    public void deleteRoom(Long id) {

        roomRepo.deleteById(id);
    }
}