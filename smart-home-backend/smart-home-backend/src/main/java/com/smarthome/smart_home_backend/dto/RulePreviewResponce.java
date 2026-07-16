package com.smarthome.smart_home_backend.dto;

public class RulePreviewResponce {
    private String ruleName;
    private String result;

    public RulePreviewResponce(String ruleName, String result) {
        this.ruleName = ruleName;
        this.result = result;
    }

    public String getRuleName() { return ruleName; }
    public String getResult() { return result; }
}