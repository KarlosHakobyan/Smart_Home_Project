package com.smarthome.smart_home_backend.service;

import com.smarthome.smart_home_backend.dto.HomeDashboardResponse;
import com.smarthome.smart_home_backend.model.Device;
import com.smarthome.smart_home_backend.repository.DeviceRepository;
import com.smarthome.smart_home_backend.repository.HomeRepository;
import com.smarthome.smart_home_backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final HomeRepository homeRepository;
    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;

    public DashboardService(
            HomeRepository homeRepository,
            RoomRepository roomRepository,
            DeviceRepository deviceRepository
    ) {
        this.homeRepository = homeRepository;
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
    }

    public HomeDashboardResponse getDashboard(Long homeId) {

        List<Device> devices = deviceRepository.findAll();

        int activeDevices = (int) devices.stream()
                .filter(Device::isActive)
                .count();

        return new HomeDashboardResponse(
                (int) homeRepository.count(),
                (int) roomRepository.count(),
                devices.size(),
                activeDevices,
                0,
                0
        );
    }

    public HomeDashboardResponse getFirstDashboard() {
        return getDashboard(null);
    }
}