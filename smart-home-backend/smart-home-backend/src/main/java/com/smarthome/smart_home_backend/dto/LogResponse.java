package com.smarthome.smart_home_backend.dto;

public class LogResponse {

    private String time;
    private String ruleName;
    private String deviceName;
    private Boolean success;
    private Integer eventId;
    private Integer actionId;

    // ✅ ДОБАВИЛИ
    private String eventType;
    private String actionType;

    public LogResponse(String time, String ruleName, String deviceName, boolean success,
                       Integer eventId, Integer actionId,
                       String eventType, String actionType) {
        this.time = time;
        this.ruleName = ruleName;
        this.deviceName = deviceName;
        this.success = success;
        this.eventId = eventId;
        this.actionId = actionId;
        this.eventType = eventType;
        this.actionType = actionType;
    }

    public String getTime() { return time; }
    public String getRuleName() { return ruleName; }
    public String getDeviceName() { return deviceName; }
    public Boolean getSuccess() { return success; }

    public Integer getEventId() { return eventId; }
    public Integer getActionId() { return actionId; }

    // ✅ новые геттеры
    public String getEventType() { return eventType; }
    public String getActionType() { return actionType; }
}