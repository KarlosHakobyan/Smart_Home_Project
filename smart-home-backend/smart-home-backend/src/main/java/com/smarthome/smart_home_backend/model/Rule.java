package com.smarthome.smart_home_backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "[Rules]")
public class Rule {

    /* =========================================================
       ID
       ========================================================= */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private Integer id;

    /* =========================================================
       HOME
       ========================================================= */

    @Column(name = "home_id")
    private Integer homeId;

    /* =========================================================
       FIELDS
       ========================================================= */

    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /* =========================================================
       CONSTRUCTORS
       ========================================================= */

    public Rule() {
    }

    public Rule(
            Integer id,
            Integer homeId,
            String ruleName,
            String description,
            Integer priority,
            String status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.homeId = homeId;
        this.ruleName = ruleName;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
    }

    /* =========================================================
       GETTERS
       ========================================================= */

    public Integer getId() {
        return id;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /* =========================================================
       SETTERS
       ========================================================= */

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}