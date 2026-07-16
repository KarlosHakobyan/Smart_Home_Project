package com.smarthome.smart_home_backend.dto;

public class HomeDashboardResponse {

    private int homes;
    private int rooms;
    private int devices;
    private int activeDevices;
    private int activeRules;
    private int recentLogs;

    public HomeDashboardResponse(
            int homes,
            int rooms,
            int devices,
            int activeDevices,
            int activeRules,
            int recentLogs
    ) {
        this.homes = homes;
        this.rooms = rooms;
        this.devices = devices;
        this.activeDevices = activeDevices;
        this.activeRules = activeRules;
        this.recentLogs = recentLogs;
    }

    public int getHomes() {
        return homes;
    }

    public int getRooms() {
        return rooms;
    }

    public int getDevices() {
        return devices;
    }

    public int getActiveDevices() {
        return activeDevices;
    }

    public int getActiveRules() {
        return activeRules;
    }

    public int getRecentLogs() {
        return recentLogs;
    }
}