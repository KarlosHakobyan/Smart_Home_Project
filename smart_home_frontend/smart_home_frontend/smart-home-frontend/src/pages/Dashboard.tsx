// src/pages/Dashboard.tsx

import { useEffect, useState } from "react";

import { getDashboard } from "../api/api";

interface DashboardData {
    homes: number;
    rooms: number;
    devices: number;
    activeDevices: number;
    activeRules: number;
    recentLogs: number;
}

const Dashboard = () => {

    const [stats, setStats] =
        useState<DashboardData | null>(null);

    const [loading, setLoading] =
        useState(true);

    useEffect(() => {

        const loadDashboard = async () => {

            try {


                const data = await getDashboard();

                setStats(data);

            } catch (error) {

                console.error(
                    "Dashboard loading error:",
                    error
                );

            } finally {

                setLoading(false);
            }
        };

        loadDashboard();

    }, []);

    if (loading) {
        return (
            <div className="p-8 text-white">
                Loading dashboard...
            </div>
        );
    }

    if (!stats) {
        return (
            <div className="p-8 text-red-400">
                Failed to load dashboard
            </div>
        );
    }

    return (
        <div
            style={{
                padding: "32px",
                color: "white",
                background: "#071225",
                minHeight: "100vh",
            }}
        >

            <h1
                style={{
                    fontSize: "38px",
                    marginBottom: "30px",
                    fontWeight: 700,
                }}
            >

            </h1>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns:
                        "repeat(auto-fit, minmax(220px, 1fr))",
                    gap: "20px",
                }}
            >

                <div style={cardStyle}>
                    <div style={titleStyle}>Homes</div>
                    <div style={valueStyle}>{stats.homes}</div>
                </div>

                {/* ROOMS */}

                <div style={cardStyle}>
                    <div style={titleStyle}>
                        Rooms
                    </div>

                    <div style={valueStyle}>
                        {stats.rooms}
                    </div>
                </div>

                {/* DEVICES */}

                <div style={cardStyle}>
                    <div style={titleStyle}>
                        Devices
                    </div>

                    <div style={valueStyle}>
                        {stats.devices}
                    </div>
                </div>

                {/* ACTIVE DEVICES */}

                <div style={cardStyle}>
                    <div style={titleStyle}>
                        Active Devices
                    </div>

                    <div style={valueStyle}>
                        {stats.activeDevices}
                    </div>
                </div>

                {/* ACTIVE RULES */}

                <div style={cardStyle}>
                    <div style={titleStyle}>
                        Active Rules
                    </div>

                    <div style={valueStyle}>
                        {stats.activeRules}
                    </div>
                </div>

                {/* RECENT LOGS */}

                <div style={cardStyle}>
                    <div style={titleStyle}>
                        Recent Logs
                    </div>

                    <div style={valueStyle}>
                        {stats.recentLogs}
                    </div>
                </div>

            </div>
        </div>
    );
};

/* =========================================================
   STYLES
   ========================================================= */

const cardStyle = {
    background: "#13233d",
    borderRadius: "18px",
    padding: "28px",
    boxShadow:
        "0 4px 20px rgba(0,0,0,0.3)",
};

const titleStyle = {
    fontSize: "18px",
    opacity: 0.8,
    marginBottom: "12px",
};

const valueStyle = {
    fontSize: "42px",
    fontWeight: 700,
};

export default Dashboard;