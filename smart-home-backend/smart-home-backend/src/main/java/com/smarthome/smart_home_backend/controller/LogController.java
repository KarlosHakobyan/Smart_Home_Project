package com.smarthome.smart_home_backend.controller;

import com.smarthome.smart_home_backend.dto.LogResponse;
import com.smarthome.smart_home_backend.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*")
public class LogController {

    private final LogService logService;

    public LogController(
            LogService logService
    ) {
        this.logService = logService;
    }

    /* =========================================================
       GET LOGS
       ========================================================= */

    @GetMapping
    public List<LogResponse> getLogs() {

        return logService.getReadableLogs();
    }
}