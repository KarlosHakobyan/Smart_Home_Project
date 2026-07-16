package com.smarthome.smart_home_backend.controller;

import com.smarthome.smart_home_backend.model.Room;
import com.smarthome.smart_home_backend.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    public RoomController(
            RoomService roomService
    ) {
        this.roomService = roomService;
    }

    /* =========================================================
       GET ALL ROOMS
       ========================================================= */

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {

        return roomService.getAllRooms();
    }

    /* =========================================================
       GET ROOM BY ID
       ========================================================= */

    @GetMapping("/rooms/{id}")
    public Room getRoomById(
            @PathVariable Long id
    ) {

        return roomService.getRoomById(id);
    }

    /* =========================================================
       GET ROOMS BY HOME
       ========================================================= */

    @GetMapping("/homes/{homeId}/rooms")
    public List<Room> getRoomsByHome(
            @PathVariable Long homeId
    ) {

        return roomService.getRoomsByHome(homeId);
    }

    /* =========================================================
       CREATE ROOM
       ========================================================= */

    @PostMapping("/rooms")
    public Room createRoom(
            @RequestBody Room room
    ) {

        return roomService.createRoom(room);
    }

    /* =========================================================
       UPDATE ROOM
       ========================================================= */

    @PutMapping("/rooms/{id}")
    public Room updateRoom(
            @PathVariable Long id,
            @RequestBody Room updated
    ) {

        return roomService.updateRoom(
                id,
                updated
        );
    }

    /* =========================================================
       DELETE ROOM
       ========================================================= */

    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(
            @PathVariable Long id
    ) {

        roomService.deleteRoom(id);
    }
}