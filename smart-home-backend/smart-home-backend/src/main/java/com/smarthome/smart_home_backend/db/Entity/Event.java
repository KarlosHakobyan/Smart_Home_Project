package com.smarthome.smart_home_backend.db.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Event", schema = "dbo")
public class Event {

    @Id
    @Column(name = "event_id")
    private Integer id;

    @Column(name = "event_type")
    private String eventType;

    public Integer getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }
}