import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import {
    getDevices,
    toggleDevice,
} from "../api/api";

interface Device {
    id: number;
    deviceName: string;
    deviceType: string;
    manufacturer: string;
    active: boolean;
}

export default function Devices() {

    const { roomId } = useParams();

    const [devices, setDevices] = useState<Device[]>([]);

    /* =========================================================
       LOAD DEVICES
       ========================================================= */

    const loadDevices = async () => {

        try {

            const response = await getDevices(
                Number(roomId)
            );

            setDevices(response.data);

        } catch (err) {

            console.error(err);

            setDevices([]);
        }
    };

    /* =========================================================
       TOGGLE DEVICE
       ========================================================= */

    const handleToggle = async (id: number) => {

        try {

            await toggleDevice(id);

            await loadDevices();

        } catch (err) {

            console.error(err);
        }
    };

    /* =========================================================
       EFFECT
       ========================================================= */

    useEffect(() => {

        loadDevices();

    }, [roomId]);

    /* =========================================================
       UI
       ========================================================= */

    return (
        <div
            style={{
                padding: "40px",
                color: "white",
                minHeight: "100vh",
                background:
                    "linear-gradient(to bottom, #021028, #041633)",
            }}
        >

            <h1
                style={{
                    fontSize: "42px",
                    marginBottom: "32px",
                    fontWeight: "bold",
                }}
            >
                ⚡ Devices
            </h1>

            {devices.length === 0 && (
                <div
                    style={{
                        fontSize: "20px",
                        opacity: 0.7,
                    }}
                >
                    No devices found
                </div>
            )}

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns:
                        "repeat(auto-fill, minmax(320px, 1fr))",
                    gap: "24px",
                }}
            >

                {devices.map((device) => (

                    <div
                        key={device.id}
                        style={{
                            background: "#152544",
                            borderRadius: "18px",
                            padding: "24px",
                            boxShadow:
                                "0 10px 30px rgba(0,0,0,0.35)",
                            transition: "0.2s",
                        }}
                    >

                        <div
                            style={{
                                display: "flex",
                                justifyContent: "space-between",
                                alignItems: "center",
                                marginBottom: "20px",
                            }}
                        >

                            <h2
                                style={{
                                    margin: 0,
                                    fontSize: "24px",
                                }}
                            >
                                {device.deviceName}
                            </h2>

                            <div
                                style={{
                                    padding:
                                        "6px 12px",
                                    borderRadius: "999px",
                                    fontSize: "14px",
                                    fontWeight: "bold",
                                    background:
                                        device.active
                                            ? "#1db954"
                                            : "#ff4d4f",
                                }}
                            >
                                {device.active
                                    ? "ON"
                                    : "OFF"}
                            </div>
                        </div>

                        <div
                            style={{
                                marginBottom: "12px",
                                opacity: 0.85,
                                fontSize: "16px",
                            }}
                        >
                            <strong>Type:</strong>
                            {" "}
                            {device.deviceType}
                        </div>

                        <div
                            style={{
                                marginBottom: "24px",
                                opacity: 0.85,
                                fontSize: "16px",
                            }}
                        >
                            <strong>Manufacturer:</strong>
                            {" "}
                            {device.manufacturer}
                        </div>

                        <button
                            onClick={() =>
                                handleToggle(device.id)
                            }
                            style={{
                                width: "100%",
                                border: "none",
                                borderRadius: "12px",
                                padding: "14px",
                                fontSize: "16px",
                                fontWeight: "bold",
                                cursor: "pointer",
                                color: "white",
                                background:
                                    device.active
                                        ? "#ff4d4f"
                                        : "#2563eb",
                                transition: "0.2s",
                            }}
                        >
                            {device.active
                                ? "Turn OFF"
                                : "Turn ON"}
                        </button>
                    </div>
                ))}

            </div>
        </div>
    );
}