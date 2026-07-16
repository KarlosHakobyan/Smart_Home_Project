package com.smarthome.smart_home_backend.ds.model;

public class SensorEvent {

    private final int sensorId;
    private final double value;
    private final long timestamp;

    public SensorEvent(int sensorId, double value) {
        this(sensorId, value, System.currentTimeMillis());
    }

    public SensorEvent(int sensorId, double value, long timestamp) {
        this.sensorId = sensorId;
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getSensorId() { return sensorId; }
    public double getValue() { return value; }
    public long getTimestamp() { return timestamp; }
}