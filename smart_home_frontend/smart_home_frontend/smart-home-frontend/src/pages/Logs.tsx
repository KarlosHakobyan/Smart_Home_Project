import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { getReadableLogs } from "../api/api";

type LogItem = {
    time?: string;
    ruleName?: string;
    success?: boolean;
    eventId?: number;
    actionId?: number;

    // ✅ новые поля
    eventType?: string;
    actionType?: string;
};

export function Logs() {
    const [logs, setLogs] = useState<LogItem[]>([]);
    const [connected, setConnected] = useState(false);

    /* LOAD */
    const load = async () => {
        try {
            const data = await getReadableLogs();

            console.log("LOGS:", data);

            setLogs(Array.isArray(data) ? data : []);
        } catch (e) {
            console.error("Failed to load logs:", e);
            setLogs([]);
        }
    };

    useEffect(() => {
        load();

        const client = new Client({
            webSocketFactory: () =>
                new SockJS("http://localhost:8080/ws"),
            reconnectDelay: 5000,
        });

        client.onConnect = () => {
            setConnected(true);

            client.subscribe("/topic/logs", (message) => {
                try {
                    const newLog = JSON.parse(message.body);
                    setLogs((prev) => [newLog, ...prev]);
                } catch (e) {
                    console.error("WS parse error:", e);
                }
            });
        };

        client.onDisconnect = () => {
            setConnected(false);
        };

        client.activate();

        return () => {
            client.deactivate();
        };
    }, []);

    const formatTime = (time?: string) =>
        time ? new Date(Number(time)).toLocaleString() : "—";

    const formatText = (v?: string) =>
        v ? v.replaceAll("_", " ") : undefined;

    return (
        <div className="p-6 bg-gray-900 min-h-screen text-white">

            <div className="flex justify-between mb-6">
                <h1 className="text-2xl">📜 Logs</h1>

                <div>
                    {connected ? (
                        <span className="text-green-400">● Live</span>
                    ) : (
                        <span className="text-red-400">● Offline</span>
                    )}
                </div>
            </div>

            <div className="bg-gray-800 rounded overflow-hidden">

                <table className="w-full text-sm">
                    <thead className="bg-gray-700">
                    <tr>
                        <th className="p-3">Time</th>
                        <th className="p-3">Rule</th>
                        <th className="p-3">Event</th>
                        <th className="p-3">Action</th>
                        <th className="p-3">Result</th>
                    </tr>
                    </thead>

                    <tbody>
                    {logs.length > 0 ? (
                        logs.map((log, i) => (
                            <tr key={i} className="border-t border-gray-700">

                                <td className="p-3 text-gray-300">
                                    {formatTime(log.time)}
                                </td>

                                <td className="p-3">
                                    {log.ruleName || "—"}
                                </td>

                                {/* ✅ EVENT */}
                                <td className="p-3 text-gray-400">
                                    {formatText(log.eventType) ||
                                        log.eventId ||
                                        "—"}
                                </td>

                                {/* ✅ ACTION */}
                                <td className="p-3 text-gray-400">
                                    {formatText(log.actionType) ||
                                        log.actionId ||
                                        "—"}
                                </td>

                                <td className="p-3">
                                    {log.success === true && (
                                        <span className="text-green-400">🟢 Success</span>
                                    )}
                                    {log.success === false && (
                                        <span className="text-red-400">🔴 Failed</span>
                                    )}
                                    {log.success === undefined && "—"}
                                </td>

                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={5} className="p-6 text-center text-gray-400">
                                No logs yet
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>

            </div>
        </div>
    );
}