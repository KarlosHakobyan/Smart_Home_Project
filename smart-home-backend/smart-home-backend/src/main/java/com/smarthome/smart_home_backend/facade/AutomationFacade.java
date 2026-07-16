package com.smarthome.smart_home_backend.facade;

import com.smarthome.smart_home_backend.db.DatabaseLoader;
import com.smarthome.smart_home_backend.ds.engine.RuleEngine;
import com.smarthome.smart_home_backend.ds.model.*;
import com.smarthome.smart_home_backend.dto.CreateRuleRequest;
import com.smarthome.smart_home_backend.dto.RulePreviewResponce;
import com.smarthome.smart_home_backend.model.Device;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AutomationFacade {

    private final RuleEngine ruleEngine =
            new RuleEngine();

    private final List<Device> devices;

    private int appliedLogCount = 0;

    public AutomationFacade() {

        this.devices =
                DatabaseLoader.loadDevices();

        ruleEngine.addRules(
                DatabaseLoader.loadRules()
        );
    }

    /* =========================================================
       DEVICES
       ========================================================= */

    public List<Device> getAllDevices() {

        return devices;
    }

    public Device toggleDevice(int id) {

        for (int i = 0; i < devices.size(); i++) {

            Device d = devices.get(i);

            if (d.getId().intValue() == id) {

                boolean newState =
                        !d.isActive();

                Device updated =
                        new Device(

                                d.getId(),

                                d.getDeviceName(),

                                d.getDeviceType(),

                                d.getManufacturer(),

                                d.getInstallationDate(),

                                newState,

                                d.getRoom()                        );

                devices.set(i, updated);

                DatabaseLoader.updateDeviceState(
                        id,
                        newState
                );

                return updated;
            }
        }

        return null;
    }

    /* =========================================================
       RULES
       ========================================================= */

    public List<Rule> getAllRules() {

        return new ArrayList<>(
                ruleEngine.getRules().values()
        );
    }

    public Rule addRule(
            CreateRuleRequest request
    ) {

        int id =
                DatabaseLoader.saveRule(request);

        Rule rule =
                new Rule(id, request.name);

        for (var c : request.conditions) {

            rule.addCondition(
                    new Condition(
                            c.sensorId,

                            ComparisonOperator.fromSymbol(
                                    c.operator
                            ),

                            c.value
                    )
            );
        }

        for (var a : request.actions) {

            rule.addAction(
                    new Action(
                            a.deviceId,
                            a.command
                    )
            );
        }

        ruleEngine.addRule(rule);

        return rule;
    }

    public void enableRule(int id) {

        ruleEngine.enableRule(id);

        DatabaseLoader.updateRuleStatus(
                id,
                "active"
        );
    }

    public void disableRule(int id) {

        ruleEngine.disableRule(id);

        DatabaseLoader.updateRuleStatus(
                id,
                "inactive"
        );
    }

    /* =========================================================
       EVENTS
       ========================================================= */

    public void triggerEvent(
            int sensorId,
            double value
    ) {

        ruleEngine.triggerEvent(
                sensorId,
                value
        );
    }

    public void processAllEvents() {

        ruleEngine.processAllEvents();

        applyLogs();
    }

    /* =========================================================
       APPLY ACTIONS
       ========================================================= */

    private void applyLogs() {

        List<ExecutionRecord> logs =
                ruleEngine.getExecutionLog();

        for (
                int i = appliedLogCount;
                i < logs.size();
                i++
        ) {

            ExecutionRecord r = logs.get(i);

            applyAction(
                    r.getDeviceId(),
                    r.getActionCommand()
            );

            DatabaseLoader.saveExecutionLog(r);
        }

        appliedLogCount = logs.size();
    }

    private void applyAction(
            int deviceId,
            String command
    ) {

        for (int i = 0; i < devices.size(); i++) {

            Device d = devices.get(i);

            if (d.getId().intValue() == deviceId) {

                boolean state =
                        d.isActive();

                if (
                        command
                                .toLowerCase()
                                .contains("on")
                ) {
                    state = true;
                }

                if (
                        command
                                .toLowerCase()
                                .contains("off")
                ) {
                    state = false;
                }

                devices.set(
                        i,

                        new Device(

                                d.getId(),

                                d.getDeviceName(),

                                d.getDeviceType(),

                                d.getManufacturer(),

                                d.getInstallationDate(),

                                state,

                                d.getRoom()
                        )
                );

                DatabaseLoader.updateDeviceState(
                        deviceId,
                        state
                );
            }
        }
    }

    /* =========================================================
       PREVIEW
       ========================================================= */

    public List<RulePreviewResponce> previewRules(
            int sensorId,
            double value
    ) {

        List<RulePreviewResponce> result =
                new ArrayList<>();

        for (
                Rule r :
                ruleEngine.getRules().values()
        ) {

            if (!r.isEnabled()) {
                continue;
            }

            for (Condition c : r.getConditions()) {

                if (
                        c.getSensorId() == sensorId &&
                                c.isSatisfied(value)
                ) {

                    for (Action a : r.getActions()) {

                        result.add(
                                new RulePreviewResponce(

                                        r.getName(),

                                        DatabaseLoader.getDeviceNameById(
                                                a.getDeviceId()
                                        ) + " will execute"
                                )
                        );
                    }
                }
            }
        }

        return result;
    }

    /* =========================================================
       LOGS
       ========================================================= */

    public List<ExecutionRecord> getExecutionLog() {

        return ruleEngine.getExecutionLog();
    }

    public Map<Integer, Double> getSensorStates() {

        return ruleEngine.getSensorStates();
    }

    public void addDependency(
            int fromRuleId,
            int toRuleId
    ) {

        ruleEngine.addDependency(
                fromRuleId,
                toRuleId
        );
    }
}