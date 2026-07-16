package com.smarthome.smart_home_backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "UserHome")
public class UserHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_home_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "home_id")
    private Long homeId;

    @Column(name = "access_role")
    private String accessRole;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    public UserHome() {
    }

    public UserHome(
            Long id,
            Long userId,
            Long homeId,
            String accessRole,
            LocalDateTime joinedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.homeId = homeId;
        this.accessRole = accessRole;
        this.joinedAt = joinedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getHomeId() {
        return homeId;
    }

    public String getAccessRole() {
        return accessRole;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}