package com.smarthome.smart_home_backend.controller;

import com.smarthome.smart_home_backend.model.Home;
import com.smarthome.smart_home_backend.model.Room;
import com.smarthome.smart_home_backend.service.EditService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/edit")
@CrossOrigin(origins = "*")
public class EditController {

    private final EditService editService;

    public EditController(
            EditService editService
    ) {
        this.editService = editService;
    }

    /* =========================================================
       HOME EDIT
       ========================================================= */

    @PutMapping("/homes/{id}")
    public Home updateHome(
            @PathVariable Long id,
            @RequestBody Home home
    ) {

        return editService.updateHome(
                id,
                home
        );
    }

    /* =========================================================
       ROOM EDIT
       ========================================================= */

    @PutMapping("/rooms/{id}")
    public Room updateRoom(
            @PathVariable Long id,
            @RequestBody Room room
    ) {

        return editService.updateRoom(
                id,
                room
        );
    }
}