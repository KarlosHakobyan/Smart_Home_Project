package com.smarthome.smart_home_backend.db.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ActionTable")
public class Action {

    @Id
    @Column(name = "action_id")
    private Integer id;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "rule_id")
    private Integer ruleId;

    @Column(name = "parameters")
    private String parameters;

    public Integer getId() { return id; }
    public String getActionType() { return actionType; }
    public Integer getDeviceId() { return deviceId; }
    public Integer getRuleId() { return ruleId; }
    public String getParameters() { return parameters; }
}