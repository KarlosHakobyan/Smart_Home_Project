package com.smarthome.smart_home_backend.ds.model;

public class Condition {

    private final int sensorId;
    private final ComparisonOperator operator;
    private final double threshold;

    public Condition(int sensorId, ComparisonOperator operator, double threshold) {
        this.sensorId = sensorId;
        this.operator = operator;
        this.threshold = threshold;
    }

    public int getSensorId() { return sensorId; }

    public boolean isSatisfied(double value) {
        return switch (operator) {
            case GREATER_THAN -> value > threshold;
            case LESS_THAN -> value < threshold;
            case GREATER_OR_EQUAL -> value >= threshold;
            case LESS_OR_EQUAL -> value <= threshold;
            case EQUAL -> value == threshold;
            case NOT_EQUAL -> value != threshold;
        };
    }
}