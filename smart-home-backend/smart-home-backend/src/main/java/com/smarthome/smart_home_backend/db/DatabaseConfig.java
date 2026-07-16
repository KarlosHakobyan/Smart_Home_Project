package com.smarthome.smart_home_backend.db;

public class DatabaseConfig {
    public static final String URL =
            "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=smart_home;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true";

    public static final String USER = "smart_home_login";
    public static final String PASSWORD = "SmartHome123!";
}

