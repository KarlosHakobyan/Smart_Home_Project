package com.smarthome.smart_home_backend.db;

import com.smarthome.smart_home_backend.model.Device;
import com.smarthome.smart_home_backend.model.Home;
import com.smarthome.smart_home_backend.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseLoader {

    public static List<Device> loadDevices() {

        List<Device> devices = new ArrayList<>();

        String query = """
            SELECT 
                device_id,
                device_name,
                device_type,
                manufacturer,
                installation_date,
                is_active,
                room_id
            FROM Device
        """;

        try (
                Connection conn = DriverManager.getConnection(
                        DatabaseConfig.URL,
                        DatabaseConfig.USER,
                        DatabaseConfig.PASSWORD
                );

                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(query)
        ) {

            while (rs.next()) {

                Date sqlDate =
                        rs.getDate("installation_date");

                devices.add(new Device(

                        rs.getLong("device_id"),

                        rs.getString("device_name"),

                        rs.getString("device_type"),

                        rs.getString("manufacturer"),

                        sqlDate != null
                                ? sqlDate.toLocalDate()
                                : null,

                        rs.getBoolean("is_active"),

                        null
                ));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return devices;
    }

    public static void updateDeviceState(
            int deviceId,
            boolean isActive
    ) {

        String query =
                "UPDATE Device SET is_active = ? WHERE device_id = ?";

        try (
                Connection conn = DriverManager.getConnection(
                        DatabaseConfig.URL,
                        DatabaseConfig.USER,
                        DatabaseConfig.PASSWORD
                );

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            ps.setBoolean(1, isActive);

            ps.setInt(2, deviceId);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static String getDeviceNameById(
            int deviceId
    ) {

        String query =
                "SELECT device_name FROM Device WHERE device_id = ?";

        try (
                Connection conn = DriverManager.getConnection(
                        DatabaseConfig.URL,
                        DatabaseConfig.USER,
                        DatabaseConfig.PASSWORD
                );

                PreparedStatement ps =
                        conn.prepareStatement(query)
        ) {

            ps.setInt(1, deviceId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getString("device_name");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Device " + deviceId;
    }

    public static int saveRule(
            Object request
    ) {
        return 1;
    }

    public static void updateRuleStatus(
            int ruleId,
            String status
    ) {}

    public static void saveExecutionLog(
            Object record
    ) {}

    public static List<com.smarthome.smart_home_backend.ds.model.Rule> loadRules() {

        return new ArrayList<>();
    }
}