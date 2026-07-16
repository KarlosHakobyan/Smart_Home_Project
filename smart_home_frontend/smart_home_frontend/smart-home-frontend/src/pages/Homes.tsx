import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    getHomes,
    createHome,
    deleteHome,
    updateHome,
} from "../api/api";

interface Home {
    id: number;
    name: string;
    address: string;
    timezone: string;
}

export default function Homes() {

    const navigate = useNavigate();

    const [homes, setHomes] = useState<Home[]>([]);

    const [name, setName] = useState("");
    const [address, setAddress] = useState("");
    const [timezone, setTimezone] =
        useState("Asia/Yerevan");

    const [search, setSearch] = useState("");

    const [loading, setLoading] =
        useState(false);

    const [showModal, setShowModal] =
        useState(false);

    const [editingHome, setEditingHome] =
        useState<Home | null>(null);

    /* =========================================================
       LOAD
       ========================================================= */

    const loadHomes = async () => {

        try {

            setLoading(true);

            const res = await getHomes();

            setHomes(
                Array.isArray(res.data)
                    ? res.data
                    : []
            );

        } catch (e) {

            console.error(e);

            setHomes([]);

        } finally {

            setLoading(false);
        }
    };

    useEffect(() => {

        loadHomes();

    }, []);

    /* =========================================================
       CREATE
       ========================================================= */

    const handleCreate = async () => {

        if (
            !name.trim() ||
            !address.trim() ||
            !timezone.trim()
        ) {
            return;
        }

        try {

            await createHome({
                name,
                address,
                timezone,
            });

            setName("");
            setAddress("");
            setTimezone("Asia/Yerevan");

            setShowModal(false);

            await loadHomes();

        } catch (e) {

            console.error(e);
        }
    };

    /* =========================================================
       EDIT
       ========================================================= */

    const handleEdit = async () => {

        if (!editingHome) {
            return;
        }

        try {

            await updateHome(
                editingHome.id,
                {
                    name: editingHome.name,
                    address: editingHome.address,
                    timezone: editingHome.timezone,
                }
            );

            setEditingHome(null);

            await loadHomes();

        } catch (e) {

            console.error(e);
        }
    };

    /* =========================================================
       DELETE
       ========================================================= */

    const handleDelete = async (
        id: number
    ) => {

        try {

            await deleteHome(id);

            await loadHomes();

        } catch (e) {

            console.error(e);
        }
    };

    /* =========================================================
       FILTER
       ========================================================= */

    const filteredHomes = useMemo(() => {

        return homes.filter((home) =>
            home.name
                ?.toLowerCase()
                .includes(
                    search.toLowerCase()
                )
        );

    }, [homes, search]);

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

            {/* HEADER */}

            <div
                style={{
                    display: "flex",
                    justifyContent:
                        "space-between",
                    alignItems: "center",
                    marginBottom: "36px",
                    flexWrap: "wrap",
                    gap: "20px",
                }}
            >

                <h1
                    style={{
                        fontSize: "56px",
                        margin: 0,
                    }}
                >
                    🏠 Homes
                </h1>

                <button
                    onClick={() =>
                        setShowModal(true)
                    }
                    style={{
                        border: "none",
                        borderRadius: "18px",
                        padding:
                            "16px 34px",
                        background:
                            "linear-gradient(135deg, #2563eb, #3b82f6)",
                        color: "white",
                        fontSize: "18px",
                        fontWeight: "bold",
                        cursor: "pointer",
                    }}
                >
                    + Add Home
                </button>
            </div>

            {/* SEARCH */}

            <input
                placeholder="Search homes..."
                value={search}
                onChange={(e) =>
                    setSearch(
                        e.target.value
                    )
                }
                style={{
                    padding: "16px 20px",
                    width: "360px",
                    borderRadius: "16px",
                    border: "none",
                    background: "#152544",
                    color: "white",
                    fontSize: "16px",
                    marginBottom: "40px",
                    outline: "none",
                }}
            />

            {/* LOADING */}

            {loading && (

                <div
                    style={{
                        marginBottom: "20px",
                        opacity: 0.7,
                    }}
                >
                    Loading homes...
                </div>
            )}

            {/* EMPTY */}

            {!loading &&
                filteredHomes.length === 0 && (

                    <div
                        style={{
                            opacity: 0.7,
                        }}
                    >
                        No homes found
                    </div>
                )}

            {/* LIST */}

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns:
                        "repeat(auto-fill, minmax(320px, 1fr))",
                    gap: "28px",
                }}
            >

                {filteredHomes.map((home) => (

                    <div
                        key={home.id}
                        style={{
                            background:
                                "#152544",
                            borderRadius:
                                "22px",
                            padding: "28px",
                            boxShadow:
                                "0 12px 32px rgba(0,0,0,0.35)",
                        }}
                    >

                        <h2
                            style={{
                                marginTop: 0,
                            }}
                        >
                            {home.name}
                        </h2>

                        <p>
                            📍 {home.address}
                        </p>

                        <p
                            style={{
                                opacity: 0.7,
                            }}
                        >
                            🌍 {home.timezone}
                        </p>

                        <div
                            style={{
                                display: "flex",
                                justifyContent:
                                    "space-between",
                                marginTop:
                                    "24px",
                            }}
                        >

                            <div
                                style={{
                                    display:
                                        "flex",
                                    gap: "14px",
                                }}
                            >

                                <button
                                    onClick={() =>
                                        navigate(
                                            `/rooms/${home.id}`
                                        )
                                    }
                                    style={{
                                        background:
                                            "transparent",
                                        border:
                                            "none",
                                        color:
                                            "#3b82f6",
                                        cursor:
                                            "pointer",
                                        fontWeight: 600,
                                    }}
                                >
                                    Open
                                </button>

                                <button
                                    onClick={() =>
                                        setEditingHome(
                                            home
                                        )
                                    }
                                    style={{
                                        background:
                                            "transparent",
                                        border:
                                            "none",
                                        color:
                                            "#facc15",
                                        cursor:
                                            "pointer",
                                        fontWeight: 600,
                                    }}
                                >
                                    Edit
                                </button>

                            </div>

                            <button
                                onClick={() =>
                                    handleDelete(
                                        home.id
                                    )
                                }
                                style={{
                                    background:
                                        "transparent",
                                    border:
                                        "none",
                                    color:
                                        "#ff4d4f",
                                    cursor:
                                        "pointer",
                                    fontWeight: 600,
                                }}
                            >
                                Delete
                            </button>
                        </div>
                    </div>
                ))}
            </div>

            {/* CREATE MODAL */}

            {showModal && (

                <div
                    style={{
                        position: "fixed",
                        inset: 0,
                        background:
                            "rgba(0,0,0,0.6)",
                        display: "flex",
                        alignItems:
                            "center",
                        justifyContent:
                            "center",
                        zIndex: 999,
                    }}
                >

                    <div
                        style={{
                            width: "420px",
                            background:
                                "#152544",
                            borderRadius:
                                "24px",
                            padding: "32px",
                        }}
                    >

                        <h2>
                            Add New Home
                        </h2>

                        <input
                            placeholder="Name"
                            value={name}
                            onChange={(e) =>
                                setName(
                                    e.target
                                        .value
                                )
                            }
                            style={inputStyle}
                        />

                        <input
                            placeholder="Address"
                            value={address}
                            onChange={(e) =>
                                setAddress(
                                    e.target
                                        .value
                                )
                            }
                            style={inputStyle}
                        />

                        <input
                            placeholder="Timezone"
                            value={timezone}
                            onChange={(e) =>
                                setTimezone(
                                    e.target
                                        .value
                                )
                            }
                            style={inputStyle}
                        />

                        <div
                            style={{
                                display:
                                    "flex",
                                justifyContent:
                                    "flex-end",
                                gap: "12px",
                            }}
                        >

                            <button
                                onClick={() =>
                                    setShowModal(
                                        false
                                    )
                                }
                                style={secondaryButton}
                            >
                                Cancel
                            </button>

                            <button
                                onClick={
                                    handleCreate
                                }
                                style={primaryButton}
                            >
                                Create
                            </button>

                        </div>
                    </div>
                </div>
            )}

            {/* EDIT MODAL */}

            {editingHome && (

                <div
                    style={{
                        position: "fixed",
                        inset: 0,
                        background:
                            "rgba(0,0,0,0.6)",
                        display: "flex",
                        alignItems:
                            "center",
                        justifyContent:
                            "center",
                        zIndex: 999,
                    }}
                >

                    <div
                        style={{
                            width: "420px",
                            background:
                                "#152544",
                            borderRadius:
                                "24px",
                            padding: "32px",
                        }}
                    >

                        <h2>
                            Edit Home
                        </h2>

                        <input
                            value={
                                editingHome.name
                            }
                            onChange={(e) =>
                                setEditingHome({
                                    ...editingHome,
                                    name:
                                    e.target
                                        .value,
                                })
                            }
                            style={inputStyle}
                        />

                        <input
                            value={
                                editingHome.address
                            }
                            onChange={(e) =>
                                setEditingHome({
                                    ...editingHome,
                                    address:
                                    e.target
                                        .value,
                                })
                            }
                            style={inputStyle}
                        />

                        <input
                            value={
                                editingHome.timezone
                            }
                            onChange={(e) =>
                                setEditingHome({
                                    ...editingHome,
                                    timezone:
                                    e.target
                                        .value,
                                })
                            }
                            style={inputStyle}
                        />

                        <div
                            style={{
                                display:
                                    "flex",
                                justifyContent:
                                    "flex-end",
                                gap: "12px",
                            }}
                        >

                            <button
                                onClick={() =>
                                    setEditingHome(
                                        null
                                    )
                                }
                                style={secondaryButton}
                            >
                                Cancel
                            </button>

                            <button
                                onClick={
                                    handleEdit
                                }
                                style={primaryButton}
                            >
                                Save
                            </button>

                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

const inputStyle = {
    width: "100%",
    padding: "16px",
    borderRadius: "14px",
    border: "none",
    background: "#0f1d38",
    color: "white",
    fontSize: "16px",
    marginBottom: "16px",
    boxSizing: "border-box" as const,
};

const primaryButton = {
    border: "none",
    borderRadius: "12px",
    padding: "14px 20px",
    background:
        "linear-gradient(135deg, #2563eb, #3b82f6)",
    color: "white",
    cursor: "pointer",
    fontWeight: 700,
};

const secondaryButton = {
    border: "none",
    borderRadius: "12px",
    padding: "14px 20px",
    background: "#334155",
    color: "white",
    cursor: "pointer",
};