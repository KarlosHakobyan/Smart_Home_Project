// @ts-ignore
import React, { useCallback, useState } from "react";
import ReactFlow, {
    Background,
    Controls,
    addEdge,
    useEdgesState,
    useNodesState,
    type Connection,
    type Edge,
} from "reactflow";
import "reactflow/dist/style.css";
import axios from "axios"; // 🔥 добавили

/* ================= DATA ================= */

const sensors = [
    { id: "motion", name: "Motion Sensor" },
    { id: "temp", name: "Temperature Sensor" },
];

const devices = [
    { id: "light", name: "Light" },
    { id: "ac", name: "Air Conditioner" },
];

/* ================= TYPES ================= */

type NodeData = {
    label: string;
    type: "sensor" | "action";
};

/* ================= COMPONENT ================= */

export default function RuleBuilder() {
    const [nodes, setNodes, onNodesChange] = useNodesState<NodeData>([]);
    const [edges, setEdges, onEdgesChange] = useEdgesState([]);

    const [selectedSensor, setSelectedSensor] = useState("");
    const [selectedDevice, setSelectedDevice] = useState("");
    const [condition, setCondition] = useState("");

    /* ===== ADD SENSOR NODE ===== */
    const addSensor = () => {
        if (!selectedSensor) return;

        const sensor = sensors.find((s) => s.id === selectedSensor);

        setNodes((nds) => [
            ...nds,
            {
                id: Date.now().toString(),
                position: { x: 100, y: 100 + nds.length * 80 },
                data: {
                    label: `IF ${sensor?.name} ${condition || ""}`,
                    type: "sensor",
                },
            },
        ]);
    };

    /* ===== ADD DEVICE NODE ===== */
    const addDevice = () => {
        if (!selectedDevice) return;

        const device = devices.find((d) => d.id === selectedDevice);

        setNodes((nds) => [
            ...nds,
            {
                id: Date.now().toString(),
                position: { x: 400, y: 100 + nds.length * 80 },
                data: {
                    label: `THEN ${device?.name}`,
                    type: "action",
                },
            },
        ]);
    };

    /* ===== CONNECT ===== */
    const onConnect = useCallback(
        (params: Connection | Edge) =>
            setEdges((eds) => addEdge(params, eds)),
        []
    );

    /* ===== BUILD RULE TEXT ===== */
    const buildRule = () => {
        const sensorNode = nodes.find((n) => n.data.type === "sensor");
        const actionNode = nodes.find((n) => n.data.type === "action");

        if (!sensorNode || !actionNode) return "Incomplete rule";

        return `${sensorNode.data.label} → ${actionNode.data.label}`;
    };

    /* ===== SAVE TO BACKEND 🔥 ===== */
    const handleSave = async () => {
        const sensorNode = nodes.find((n) => n.data.type === "sensor");
        const actionNode = nodes.find((n) => n.data.type === "action");

        if (!sensorNode || !actionNode) {
            alert("Rule incomplete");
            return;
        }

        const rule = {
            sensor: sensorNode.data.label,
            action: actionNode.data.label,
        };

        try {
            await axios.post("http://localhost:8080/rules", rule);
            alert("Saved 🚀");
        } catch (e) {
            console.error(e);
            alert("Error saving rule");
        }
    };

    return (
        <div className="flex h-screen bg-gray-900 text-white">

            {/* ================= SIDEBAR ================= */}
            <div className="w-80 p-4 border-r border-gray-700">
                <h2 className="text-xl mb-4">Rule Builder</h2>

                {/* SENSOR */}
                <div className="mb-4">
                    <label>Sensor</label>
                    <select
                        className="w-full mt-1 p-2 bg-gray-800"
                        value={selectedSensor}
                        onChange={(e) => setSelectedSensor(e.target.value)}
                    >
                        <option value="">Select sensor</option>
                        {sensors.map((s) => (
                            <option key={s.id} value={s.id}>
                                {s.name}
                            </option>
                        ))}
                    </select>

                    <input
                        className="w-full mt-2 p-2 bg-gray-800"
                        placeholder="Condition (e.g > 25)"
                        value={condition}
                        onChange={(e) => setCondition(e.target.value)}
                    />

                    <button
                        onClick={addSensor}
                        className="mt-2 w-full bg-blue-600 p-2 rounded"
                    >
                        Add Sensor
                    </button>
                </div>

                {/* DEVICE */}
                <div className="mb-4">
                    <label>Device</label>
                    <select
                        className="w-full mt-1 p-2 bg-gray-800"
                        value={selectedDevice}
                        onChange={(e) => setSelectedDevice(e.target.value)}
                    >
                        <option value="">Select device</option>
                        {devices.map((d) => (
                            <option key={d.id} value={d.id}>
                                {d.name}
                            </option>
                        ))}
                    </select>

                    <button
                        onClick={addDevice}
                        className="mt-2 w-full bg-green-600 p-2 rounded"
                    >
                        Add Action
                    </button>
                </div>

                {/* RULE PREVIEW */}
                <div className="mt-6 p-3 bg-gray-800 rounded">
                    <div className="text-sm text-gray-400 mb-1">Preview</div>
                    <div>{buildRule()}</div>
                </div>

                {/* 🔥 ORANGE SAVE BUTTON */}
                <button
                    onClick={handleSave}
                    className="mt-4 w-full bg-orange-500 hover:bg-orange-400 p-3 rounded font-semibold"
                >
                    Save Rule 💾
                </button>
            </div>

            {/* ================= FLOW ================= */}
            <div className="flex-1">
                <ReactFlow
                    nodes={nodes}
                    edges={edges}
                    onNodesChange={onNodesChange}
                    onEdgesChange={onEdgesChange}
                    onConnect={onConnect}
                    fitView
                >
                    <Background />
                    <Controls />
                </ReactFlow>
            </div>
        </div>
    );
}