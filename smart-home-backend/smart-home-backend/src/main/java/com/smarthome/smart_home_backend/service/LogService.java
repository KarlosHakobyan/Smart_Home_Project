package com.smarthome.smart_home_backend.service;

import com.smarthome.smart_home_backend.db.Entity.ExecutionLog;
import com.smarthome.smart_home_backend.dto.LogResponse;
import com.smarthome.smart_home_backend.facade.AutomationFacade;
import com.smarthome.smart_home_backend.repository.ActionRepository;
import com.smarthome.smart_home_backend.repository.EventRepository;
import com.smarthome.smart_home_backend.repository.ExecutionLogRepository;
import com.smarthome.smart_home_backend.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Service
public class LogService {

    private final AutomationFacade automationFacade;

    private final ExecutionLogRepository logRepository;
    private final EventRepository eventRepository;
    private final ActionRepository actionRepository;
    private final RuleRepository ruleRepository;

    public LogService(
            AutomationFacade automationFacade,
            ExecutionLogRepository logRepository,
            EventRepository eventRepository,
            ActionRepository actionRepository,
            RuleRepository ruleRepository
    ) {
        this.automationFacade = automationFacade;
        this.logRepository = logRepository;
        this.eventRepository = eventRepository;
        this.actionRepository = actionRepository;
        this.ruleRepository = ruleRepository;
    }

    /* =========================================================
       GET READABLE LOGS
       ========================================================= */

    public List<LogResponse> getReadableLogs() {

        List<ExecutionLog> dbLogs =
                logRepository.findAll();

        /* =====================================================
           DATABASE LOGS
           ===================================================== */

        if (!dbLogs.isEmpty()) {

            return dbLogs.stream()
                    .map(log -> {

                        /* ================= EVENT ================= */

                        String eventType = "-";

                        if (log.getEventId() != null) {

                            eventType = eventRepository
                                    .findById(log.getEventId())
                                    .map(event ->
                                            event.getEventType()
                                    )
                                    .orElse(
                                            "Event #" + log.getEventId()
                                    );
                        }

                        /* ================= ACTION ================= */

                        String actionType = "-";

                        if (log.getActionId() != null) {

                            actionType = actionRepository
                                    .findById(log.getActionId())
                                    .map(action ->
                                            action.getActionType()
                                    )
                                    .orElse(
                                            "Action #" + log.getActionId()
                                    );
                        }

                        /* ================= RULE ================= */

                        String ruleText =
                                "Rule #" + log.getRuleId();

                        if (log.getRuleId() != null) {

                            ruleText = ruleRepository
                                    .findById(log.getRuleId())
                                    .map(rule -> {

                                        String ruleName =
                                                rule.getRuleName() != null
                                                        ? rule.getRuleName()
                                                        : "Unnamed Rule";

                                        String description =
                                                rule.getDescription() != null
                                                        ? rule.getDescription()
                                                        : "";

                                        return ruleName +
                                                " — " +
                                                description;
                                    })
                                    .orElse(
                                            "Rule #" + log.getRuleId()
                                    );
                        }

                        /* ================= STATUS ================= */

                        boolean success =
                                "success".equalsIgnoreCase(
                                        log.getStatus()
                                );

                        /* ================= TIMESTAMP ================= */

                        String timestamp = String.valueOf(
                                log.getExecutionTime()
                                        .atZone(
                                                ZoneId.systemDefault()
                                        )
                                        .toInstant()
                                        .toEpochMilli()
                        );

                        return new LogResponse(
                                timestamp,
                                ruleText,
                                "-",
                                success,
                                log.getEventId(),
                                log.getActionId(),
                                eventType,
                                actionType
                        );
                    })
                    .toList();
        }

        /* =====================================================
           FALLBACK MEMORY LOGS
           ===================================================== */

        return automationFacade
                .getExecutionLog()
                .stream()
                .map(record -> new LogResponse(
                        String.valueOf(
                                record.getTimestamp()
                        ),
                        record.getRuleName(),
                        "-",
                        "SUCCESS".equalsIgnoreCase(
                                record.getStatus()
                        ),
                        null,
                        null,
                        "-",
                        "-"
                ))
                .toList();
    }
}