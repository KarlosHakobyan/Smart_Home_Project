package com.smarthome.smart_home_backend.ds.engine;

import com.smarthome.smart_home_backend.ds.model.*;
import com.smarthome.smart_home_backend.ds.structures.*;

import java.util.*;

public class RuleEngine {

    private final Map<Integer, Rule> rules = new HashMap<>();
    private SensorTrie sensorTrie = new SensorTrie();
    private final EventQueue eventQueue = new EventQueue();
    private final RuleGraph ruleGraph = new RuleGraph();
    private final List<ExecutionRecord> executionLog = new ArrayList<>();
    private final Map<Integer, Double> sensorStates = new HashMap<>();

    public void addRule(Rule rule) {
        rules.put(rule.getRuleId(), rule);
        sensorTrie.insertRule(rule);
        ruleGraph.addRule(rule.getRuleId());
    }

    public void addRules(List<Rule> rulesList) {
        for (Rule r : rulesList) addRule(r);
    }

    public void triggerEvent(int sensorId, double value) {
        eventQueue.enqueue(new SensorEvent(sensorId, value));
    }

    public void processAllEvents() {
        while (!eventQueue.isEmpty()) {
            SensorEvent e = eventQueue.dequeue();
            sensorStates.put(e.getSensorId(), e.getValue());

            List<Rule> matched = sensorTrie.getRulesBySensorId(e.getSensorId());

            for (Rule r : matched) {
                if (!r.isEnabled()) continue;

                boolean ok = true;
                for (Condition c : r.getConditions()) {
                    Double val = sensorStates.get(c.getSensorId());
                    if (val == null || !c.isSatisfied(val)) {
                        ok = false;
                        break;
                    }
                }

                if (ok) executeRule(r, e);
            }
        }
    }

    private void executeRule(Rule rule, SensorEvent event) {
        for (Action a : rule.getActions()) {
            executionLog.add(new ExecutionRecord(
                    rule.getRuleId(),
                    rule.getName(),
                    event.getSensorId(),
                    event.getValue(),
                    a.getCommand(),
                    a.getDeviceId(),
                    System.currentTimeMillis(),
                    "SUCCESS"
            ));
        }
    }

    public Map<Integer, Rule> getRules() {
        return rules;
    }

    public List<ExecutionRecord> getExecutionLog() {
        return executionLog;
    }

    public Map<Integer, Double> getSensorStates() {
        return sensorStates;
    }

    public void enableRule(int id) {
        rules.get(id).enable();
    }

    public void disableRule(int id) {
        rules.get(id).disable();
    }

    public void addDependency(int from, int to) {
        ruleGraph.addDependency(from, to);
    }
}