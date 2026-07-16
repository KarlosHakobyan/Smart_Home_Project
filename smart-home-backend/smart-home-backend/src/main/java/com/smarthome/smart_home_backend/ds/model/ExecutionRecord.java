package com.smarthome.smart_home_backend.ds.model;

public class ExecutionRecord {

    private final int ruleId;
    private final String ruleName;
    private final int sensorId;
    private final double sensorValue;
    private final String actionCommand;
    private final int deviceId;
    private final long timestamp;
    private final String status;

    public ExecutionRecord(int ruleId,
                           String ruleName,
                           int sensorId,
                           double sensorValue,
                           String actionCommand,
                           int deviceId,
                           long timestamp,
                           String status) {
        this.ruleId = ruleId;
        this.ruleName = ruleName;
        this.sensorId = sensorId;
        this.sensorValue = sensorValue;
        this.actionCommand = actionCommand;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getActionCommand() { return actionCommand; }
    public int getRuleId() { return ruleId; }
    public String getRuleName() { return ruleName; }
    public int getSensorId() { return sensorId; }
    public double getSensorValue() { return sensorValue; }
    public int getDeviceId() { return deviceId; }
    public long getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
}