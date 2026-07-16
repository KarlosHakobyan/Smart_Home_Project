1. How to Run the Backend
Requirements
Java 17+
SQL Server running locally
IntelliJ (recommended)
Database Setup

Database name:

smart_home

Make sure to update credentials in:

DatabaseConfig.java
public static final String URL =
    "jdbc:sqlserver://localhost:1433;databaseName=smart_home;encrypt=true;trustServerCertificate=true";

public static final String USER = "smart_home_login";
public static final String PASSWORD = "YOUR_PASSWORD";
Run Application
Open project in IntelliJ
Run main Spring Boot class

Backend runs at:

http://localhost:8080


2. Demo Data (for testing)

Use:

user_id = 5
home_id = (use returned value from API)
room_id = (use returned value)
device_id = (use returned value)
5. API Endpoints
User & Homes
GET /users/{userId}/homes

Returns all homes for a user.

Dashboard
GET /homes/{homeId}/dashboard

Returns summary:

{
  "rooms": 4,
  "devices": 12,
  "activeDevices": 7,
  "activeRules": 3,
  "recentLogs": 5
}
Rooms & Devices
GET /homes/{homeId}/rooms
GET /rooms/{roomId}/devices
Device Control
PUT /devices/{id}/toggle
POST /devices/{id}/test
toggle → changes device state
test → turns ON for 3 seconds, then OFF

Rules
GET /rules
POST /rules
PUT /rules/{id}/enable
PUT /rules/{id}/disable

Create Rule (example)
{
  "name": "Heating Rule",
  "homeId": 5,
  "conditions": [
    {
      "sensorId": 1,
      "operator": "<",
      "value": 10
    }
  ],
  "actions": [
    {
      "deviceId": 2,
      "command": "turn_on"
    }
  ]
}
Rule Preview (simulation)
POST /rules/preview

Request:

{
  "sensorId": 1,
  "value": 8
}

Response:

[
  {
    "ruleName": "Heating Rule",
    "result": "Bedroom Heater will turn ON"
  }
]

⚠️ Does NOT execute actions.

Logs (Explainable)
GET /logs/details
GET /homes/{homeId}/logs/details

Response:

{
  "time": "2026-04-30 15:42:10",
  "action": "Bedroom Heater turned ON",
  "reason": "Temperature was 8°C, which is below the 10°C threshold",
  "ruleName": "Heating Rule",
  "status": "success"
}
Events
GET /events/trigger/{sensorId}/{value}
GET /events/process
Dependencies (rule chaining)
GET /dependency-graph
GET /dependencies/add/{fromRuleId}/{toRuleId}


3. Core Concepts
Explainable Automation

Instead of technical logs, the system provides:

Action — Reason

Example:

Bedroom Heater turned ON — Temperature was 8°C (below threshold)
Automation Engine

The system processes events using:

Event Queue → handles incoming events
Sensor-to-Rules Index → finds matching rules
Rule Dependency Graph → supports rule chains
Preview & Testing
Preview → shows what WOULD happen
Test Device → safely tests device behavior


4. Important Notes
Some endpoints use PUT (not GET) for state changes
Database must be running before backend
Device state and logs are persistent
Preview does NOT modify system state


5. Goal of the System

This system focuses on:

automation
explainability
user understanding

It not only executes actions but also explains why they happened, improving transparency and trust.