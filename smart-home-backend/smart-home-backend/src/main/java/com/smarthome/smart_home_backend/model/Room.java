package com.smarthome.smart_home_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Room")
public class Room {

    /* =========================================================
       ID
       ========================================================= */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    /* =========================================================
       FIELDS
       ========================================================= */

    @Column(name = "room_name")
    private String name;

    @Column(name = "floor")
    private Integer floor;

    /* =========================================================
       HOME RELATION
       ========================================================= */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id")
    private Home home;

    /* =========================================================
       DEVICE RELATION
       ========================================================= */

    @OneToMany(
            mappedBy = "room",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Device> devices;

    /* =========================================================
       CONSTRUCTORS
       ========================================================= */

    public Room() {
    }

    /* =========================================================
       GETTERS
       ========================================================= */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getFloor() {
        return floor;
    }

    public Home getHome() {
        return home;
    }

    public List<Device> getDevices() {
        return devices;
    }

    /* =========================================================
       SETTERS
       ========================================================= */

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}