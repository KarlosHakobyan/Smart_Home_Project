package com.smarthome.smart_home_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Home")
public class Home {

    /* =========================================================
       ID
       ========================================================= */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_id")
    private Long id;

    /* =========================================================
       FIELDS
       ========================================================= */

    @Column(name = "home_name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "timezone")
    private String timezone;

    // ✅ FIX

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /* =========================================================
       RELATIONS
       ========================================================= */

    @OneToMany(
            mappedBy = "home",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Room> rooms;

    /* =========================================================
       CONSTRUCTORS
       ========================================================= */

    public Home() {
    }

    public Home(
            Long id,
            String name,
            String address,
            String timezone
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.timezone = timezone;
    }

    /* =========================================================
       AUTO CREATE DATE
       ========================================================= */

    @PrePersist
    public void prePersist() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
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

    public String getAddress() {
        return address;
    }

    public String getTimezone() {
        return timezone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Room> getRooms() {
        return rooms;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}