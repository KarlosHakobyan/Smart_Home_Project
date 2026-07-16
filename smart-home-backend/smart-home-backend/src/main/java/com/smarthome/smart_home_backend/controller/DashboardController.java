package com.smarthome.smart_home_backend.controller;

import com.smarthome.smart_home_backend.dto.HomeDashboardResponse;
import com.smarthome.smart_home_backend.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public HomeDashboardResponse getDashboard(
            @RequestParam(required = false)
            Long homeId
    ) {

        if (homeId == null) {
            return dashboardService.getFirstDashboard();
        }

        return dashboardService.getDashboard(
                homeId
        );
    }
}