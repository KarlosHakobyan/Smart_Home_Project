package com.smarthome.smart_home_backend.db.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ExecutionLog", schema = "dbo")
public class ExecutionLog {

    @Id
    @Column(name = "log_id")
    private Long id;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "action_id")
    private Integer actionId;



    @Column(name = "rule_id")
    private Integer ruleId;

    @Column(name = "execution_time")
    private LocalDateTime executionTime;

    @Column(name = "status")
    private String status;

    @Column(name = "error_message")
    private String errorMessage;

    public Long getId() { return id; }
    public Integer getRuleId() { return ruleId; }
    public LocalDateTime getExecutionTime() { return executionTime; }
    public String getStatus() { return status; }
    public String getErrorMessage() { return errorMessage; }
    public Integer getEventId() { return eventId; }
    public Integer getActionId() { return actionId; }
}