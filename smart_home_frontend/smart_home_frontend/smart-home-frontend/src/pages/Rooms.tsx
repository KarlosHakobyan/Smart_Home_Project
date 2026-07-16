import { useEffect, useMemo, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
    getRooms,
    createRoom,
    deleteRoom,
    updateRoom,
} from "../api/api";

interface Room {
    id: number;
    name: string;
    floor: number;
}

export default function Rooms() {

    const { homeId } = useParams();

    const navigate = useNavigate();

    const [rooms, setRooms] = useState<Room[]>([]);

    const [roomName, setRoomName] =
        useState("");

    const [floor, setFloor] =
        useState(1);

    const [search, setSearch] =
        useState("");

    const [loading, setLoading] =
        useState(false);

    const [showModal, setShowModal] =
        useState(false);

    const [editingRoom, setEditingRoom] =
        useState<Room | null>(null);

    /* =========================================================
       LOAD
       ========================================================= */

    const loadRooms = async () => {

        try {

            setLoading(true);

            const response =
                await getRooms(
                    Number(homeId)
                );

            setRooms(
                Array.isArray(
                    response.data
                )
                    ? response.data
                    : []
            );

        } catch (err) {

            console.error(err);

            setRooms([]);

        } finally {

            setLoading(false);
        }
    };

    useEffect(() => {

        loadRooms();

    }, [homeId]);

    /* =========================================================
       CREATE
       ========================================================= */

    const handleCreateRoom =
        async () => {

            try {

                await createRoom({
                    name: roomName,
                    floor,

                    homeId:
                        Number(
                            homeId
                        ),
                });

                setRoomName("");
                setFloor(1);

                setShowModal(false);

                await loadRooms();

            } catch (err) {

                console.error(
                    "Create room error",
                    err
                );
            }
        };

    /* =========================================================
       EDIT
       ========================================================= */

    const handleEditRoom =
        async () => {

            if (!editingRoom) {
                return;
            }

            try {

                await updateRoom(
                    editingRoom.id,
                    editingRoom
                );

                setEditingRoom(null);

                await loadRooms();

            } catch (err) {

                console.error(
                    "Update room error",
                    err
                );
            }
        };

    /* =========================================================
       DELETE
       ========================================================= */

    const handleDeleteRoom =
        async (id: number) => {

            try {

                await deleteRoom(id);

                await loadRooms();

            } catch (err) {

                console.error(err);
            }
        };

    /* =========================================================
       FILTER
       ========================================================= */

    const filteredRooms =
        useMemo(() => {

            return rooms.filter(
                (room) =>
                    room.name
                        ?.toLowerCase()
                        .includes(
                            search.toLowerCase()
                        )
            );

        }, [rooms, search]);

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
                    alignItems:
                        "center",
                    marginBottom:
                        "36px",
                }}
            >

                <h1
                    style={{
                        fontSize: "56px",
                        margin: 0,
                    }}
                >
                    🏠 Rooms
                </h1>

                <button
                    onClick={() =>
                        setShowModal(
                            true
                        )
                    }
                    style={{
                        border: "none",
                        borderRadius:
                            "18px",
                        padding:
                            "16px 34px",
                        background:
                            "linear-gradient(135deg, #2563eb, #3b82f6)",
                        color: "white",
                        fontSize:
                            "18px",
                        fontWeight:
                            "bold",
                        cursor:
                            "pointer",
                    }}
                >
                    + Add Room
                </button>
            </div>

            {/* SEARCH */}

            <input
                placeholder="Search rooms..."
                value={search}
                onChange={(e) =>
                    setSearch(
                        e.target.value
                    )
                }
                style={{
                    padding:
                        "16px 20px",
                    width: "360px",
                    borderRadius:
                        "16px",
                    border: "none",
                    background:
                        "#152544",
                    color: "white",
                    fontSize:
                        "16px",
                    marginBottom:
                        "40px",
                }}
            />

            {/* LOADING */}

            {loading && (
                <div>
                    Loading rooms...
                </div>
            )}

            {/* EMPTY */}

            {!loading &&
                filteredRooms.length ===
                0 && (
                    <div>
                        No rooms found
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

                {filteredRooms.map(
                    (room) => (

                        <div
                            key={
                                room.id
                            }
                            style={{
                                background:
                                    "#152544",
                                borderRadius:
                                    "22px",
                                padding:
                                    "28px",
                            }}
                        >

                            <h2>
                                {
                                    room.name
                                }
                            </h2>

                            <p>
                                🏢 Floor{" "}
                                {
                                    room.floor
                                }
                            </p>

                            <div
                                style={{
                                    display:
                                        "flex",
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
                                                `/devices/${room.id}`
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
                                        }}
                                    >
                                        Open
                                    </button>

                                    <button
                                        onClick={() =>
                                            setEditingRoom(
                                                room
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
                                        }}
                                    >
                                        Edit
                                    </button>

                                </div>

                                <button
                                    onClick={() =>
                                        handleDeleteRoom(
                                            room.id
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
                                    }}
                                >
                                    Delete
                                </button>
                            </div>
                        </div>
                    )
                )}
            </div>

            {/* CREATE MODAL */}

            {showModal && (

                <div
                    style={overlay}
                >

                    <div
                        style={modal}
                    >

                        <h2>
                            Add New Room
                        </h2>

                        <input
                            placeholder="Room name"
                            value={
                                roomName
                            }
                            onChange={(
                                e
                            ) =>
                                setRoomName(
                                    e
                                        .target
                                        .value
                                )
                            }
                            style={
                                inputStyle
                            }
                        />

                        <input
                            type="number"
                            placeholder="Floor"
                            value={
                                floor
                            }
                            onChange={(
                                e
                            ) =>
                                setFloor(
                                    Number(
                                        e
                                            .target
                                            .value
                                    )
                                )
                            }
                            style={
                                inputStyle
                            }
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
                            >
                                Cancel
                            </button>

                            <button
                                onClick={
                                    handleCreateRoom
                                }
                            >
                                Create
                            </button>

                        </div>
                    </div>
                </div>
            )}

            {/* EDIT MODAL */}

            {editingRoom && (

                <div
                    style={overlay}
                >

                    <div
                        style={modal}
                    >

                        <h2>
                            Edit Room
                        </h2>

                        <input
                            value={
                                editingRoom.name
                            }
                            onChange={(
                                e
                            ) =>
                                setEditingRoom(
                                    {
                                        ...editingRoom,
                                        name:
                                        e
                                            .target
                                            .value,
                                    }
                                )
                            }
                            style={
                                inputStyle
                            }
                        />

                        <input
                            type="number"
                            value={
                                editingRoom.floor
                            }
                            onChange={(
                                e
                            ) =>
                                setEditingRoom(
                                    {
                                        ...editingRoom,
                                        floor:
                                            Number(
                                                e
                                                    .target
                                                    .value
                                            ),
                                    }
                                )
                            }
                            style={
                                inputStyle
                            }
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
                                    setEditingRoom(
                                        null
                                    )
                                }
                            >
                                Cancel
                            </button>

                            <button
                                onClick={
                                    handleEditRoom
                                }
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

const overlay = {
    position: "fixed" as const,
    inset: 0,
    background:
        "rgba(0,0,0,0.6)",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
};

const modal = {
    width: "420px",
    background: "#152544",
    borderRadius: "24px",
    padding: "32px",
};

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