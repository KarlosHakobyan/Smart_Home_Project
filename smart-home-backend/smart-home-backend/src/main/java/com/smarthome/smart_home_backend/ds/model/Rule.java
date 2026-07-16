package com.smarthome.smart_home_backend.ds.model;

import java.util.ArrayList;
import java.util.List;

public class Rule {

    private final int ruleId;
    private final String name;
    private final List<Condition> conditions = new ArrayList<>();
    private final List<Action> actions = new ArrayList<>();
    private boolean enabled = true;

    public Rule(int ruleId, String name) {
        this.ruleId = ruleId;
        this.name = name;
    }

    public int getRuleId() { return ruleId; }
    public String getName() { return name; }

    public List<Condition> getConditions() { return conditions; }
    public List<Action> getActions() { return actions; }

    public boolean isEnabled() { return enabled; }

    public void enable() { enabled = true; }
    public void disable() { enabled = false; }

    public void addCondition(Condition c) { conditions.add(c); }
    public void addAction(Action a) { actions.add(a); }
}