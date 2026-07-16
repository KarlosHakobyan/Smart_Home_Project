import { useEffect, useState } from "react";
import { getRules } from "../api/api";

interface Rule {
    id: number;
    homeId: number;
    ruleName: string;
    description: string;
    priority: number;
    status: string;
}

export default function Rules() {

    const [rules, setRules] = useState<Rule[]>([]);
    const [search, setSearch] = useState("");

    const loadRules = async () => {

        try {

            const res = await getRules();

            setRules(res.data);

        } catch (e) {

            console.error(e);
        }
    };

    useEffect(() => {
        loadRules();
    }, []);

    const filteredRules = rules.filter((rule) =>
        rule.ruleName
            .toLowerCase()
            .includes(search.toLowerCase())
    );

    return (
        <div
            style={{
                padding: "40px",
                color: "white",
                minHeight: "100vh",
                background:
                    "linear-gradient(to bottom, #020617, #001233)",
            }}
        >

            {/* HEADER */}

            <div
                style={{
                    display: "flex",
                    alignItems: "center",
                    gap: "20px",
                    marginBottom: "40px",
                }}
            >
                <div style={{ fontSize: "64px" }}>
                    📜
                </div>

                <h1
                    style={{
                        fontSize: "72px",
                        fontWeight: 300,
                        margin: 0,
                    }}
                >
                    Rules
                </h1>
            </div>

            {/* SEARCH */}

            <input
                type="text"
                placeholder="Search rules..."
                value={search}
                onChange={(e) =>
                    setSearch(e.target.value)
                }
                style={{
                    width: "100%",
                    maxWidth: "500px",
                    padding: "18px",
                    borderRadius: "18px",
                    border: "none",
                    outline: "none",
                    background: "#172554",
                    color: "white",
                    fontSize: "18px",
                    marginBottom: "40px",
                }}
            />

            {/* RULES */}

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns:
                        "repeat(auto-fill, minmax(420px, 1fr))",
                    gap: "24px",
                }}
            >

                {filteredRules.map((rule) => (

                    <div
                        key={rule.id}
                        style={{
                            background: "#172554",
                            borderRadius: "24px",
                            padding: "24px",
                            boxShadow:
                                "0 0 20px rgba(0,0,0,0.3)",
                        }}
                    >

                        <h2
                            style={{
                                marginTop: 0,
                                marginBottom: "16px",
                            }}
                        >
                            {rule.ruleName}
                        </h2>

                        <p
                            style={{
                                color: "#cbd5e1",
                            }}
                        >
                            {rule.description}
                        </p>

                        <div
                            style={{
                                marginTop: "18px",
                                display: "flex",
                                flexDirection: "column",
                                gap: "8px",
                            }}
                        >

                            <div>
                                <strong>Home ID:</strong>
                                {" "}
                                {rule.homeId}
                            </div>

                            <div>
                                <strong>Priority:</strong>
                                {" "}
                                {rule.priority}
                            </div>

                            <div>
                                <strong>Status:</strong>
                                {" "}

                                <span
                                    style={{
                                        color:
                                            rule.status === "active"
                                                ? "#22c55e"
                                                : "#ef4444",
                                        fontWeight: 600,
                                    }}
                                >
                                    {rule.status}
                                </span>
                            </div>

                        </div>

                    </div>
                ))}

            </div>

        </div>
    );
}