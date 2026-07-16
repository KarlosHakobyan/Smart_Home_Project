package com.smarthome.smart_home_backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Device")
public class Device {

    /* =========================================================
       ID
       ========================================================= */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long id;

    /* =========================================================
       FIELDS
       ========================================================= */

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "is_active")
    private boolean active;

    /* =========================================================
       ROOM RELATION
       ========================================================= */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    /* =========================================================
       CONSTRUCTORS
       ========================================================= */

    public Device() {
    }

    public Device(
            Long id,
            String deviceName,
            String deviceType,
            String manufacturer,
            LocalDate installationDate,
            boolean active,
            Room room
    ) {
        this.id = id;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.installationDate = installationDate;
        this.active = active;
        this.room = room;
    }

    /* =========================================================
       GETTERS
       ========================================================= */

    public Long getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    public boolean isActive() {
        return active;
    }

    public Room getRoom() {
        return room;
    }

    /* =========================================================
       HELPERS
       ========================================================= */

    public Long getRoomId() {

        return room != null
                ? room.getId()
                : null;
    }

    /* =========================================================
       SETTERS
       ========================================================= */

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}