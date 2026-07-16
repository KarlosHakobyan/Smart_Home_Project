package com.smarthome.smart_home_backend.controller;

import com.smarthome.smart_home_backend.model.Device;
import com.smarthome.smart_home_backend.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "*")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(
            DeviceService deviceService
    ) {
        this.deviceService = deviceService;
    }

    /* =========================================================
       GET ALL
       ========================================================= */

    @GetMapping
    public List<Device> getAllDevices() {

        return deviceService.getAllDevices();
    }

    /* =========================================================
       GET BY ID
       ========================================================= */

    @GetMapping("/{id}")
    public Device getDeviceById(
            @PathVariable Long id
    ) {

        return deviceService.getDeviceById(id);
    }

    /* =========================================================
       GET BY ROOM
       ========================================================= */

    @GetMapping("/room/{roomId}")
    public List<Device> getDevicesByRoom(
            @PathVariable Long roomId
    ) {

        return deviceService.getDevicesByRoom(roomId);
    }

    /* =========================================================
       CREATE
       ========================================================= */

    @PostMapping
    public Device createDevice(
            @RequestBody Device device
    ) {

        return deviceService.createDevice(device);
    }

    /* =========================================================
       UPDATE
       ========================================================= */

    @PutMapping("/{id}")
    public Device updateDevice(
            @PathVariable Long id,
            @RequestBody Device updated
    ) {

        return deviceService.updateDevice(
                id,
                updated
        );
    }

    /* =========================================================
       DELETE
       ========================================================= */

    @DeleteMapping("/{id}")
    public void deleteDevice(
            @PathVariable Long id
    ) {

        deviceService.deleteDevice(id);
    }

    /* =========================================================
       TOGGLE
       ========================================================= */

    @PostMapping("/{id}/toggle")
    public Device toggleDevice(
            @PathVariable Long id
    ) {

        return deviceService.toggleDevice(id);
    }
}