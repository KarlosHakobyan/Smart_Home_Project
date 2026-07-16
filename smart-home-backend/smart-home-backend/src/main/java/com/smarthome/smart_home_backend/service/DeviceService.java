package com.smarthome.smart_home_backend.service;

import com.smarthome.smart_home_backend.model.Device;
import com.smarthome.smart_home_backend.model.Room;
import com.smarthome.smart_home_backend.repository.DeviceRepository;
import com.smarthome.smart_home_backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepo;
    private final RoomRepository roomRepo;

    public DeviceService(
            DeviceRepository deviceRepo,
            RoomRepository roomRepo
    ) {
        this.deviceRepo = deviceRepo;
        this.roomRepo = roomRepo;
    }

    /* =========================================================
       GET ALL
       ========================================================= */

    public List<Device> getAllDevices() {

        return deviceRepo.findAll();
    }

    /* =========================================================
       GET BY ID
       ========================================================= */

    public Device getDeviceById(Long id) {

        return deviceRepo.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Device not found with id: " + id
                        )
                );
    }

    /* =========================================================
       GET BY ROOM
       ========================================================= */

    public List<Device> getDevicesByRoom(Long roomId) {

        return deviceRepo.findByRoom_Id(roomId);
    }

    /* =========================================================
       CREATE
       ========================================================= */

    public Device createDevice(Device device) {

        if (
                device.getRoom() == null ||
                        device.getRoom().getId() == null
        ) {

            throw new RuntimeException(
                    "Room id is required"
            );
        }

        Long roomId = device
                .getRoom()
                .getId();

        Room room = roomRepo.findById(roomId)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Room not found with id: " + roomId
                        )
                );

        device.setRoom(room);

        return deviceRepo.save(device);
    }

    /* =========================================================
       UPDATE
       ========================================================= */

    public Device updateDevice(
            Long id,
            Device updated
    ) {

        Device device = deviceRepo.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Device not found with id: " + id
                        )
                );

        device.setDeviceName(
                updated.getDeviceName()
        );

        device.setDeviceType(
                updated.getDeviceType()
        );

        device.setManufacturer(
                updated.getManufacturer()
        );

        device.setInstallationDate(
                updated.getInstallationDate()
        );

        device.setActive(
                updated.isActive()
        );

        return deviceRepo.save(device);
    }

    /* =========================================================
       DELETE
       ========================================================= */

    public void deleteDevice(Long id) {

        deviceRepo.deleteById(id);
    }

    /* =========================================================
       TOGGLE
       ========================================================= */

    public Device toggleDevice(Long id) {

        Device device = deviceRepo.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Device not found with id: " + id
                        )
                );

        device.setActive(
                !device.isActive()
        );

        return deviceRepo.save(device);
    }
}