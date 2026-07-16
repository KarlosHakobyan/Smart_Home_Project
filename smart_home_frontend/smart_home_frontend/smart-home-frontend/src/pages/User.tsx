import { useEffect, useMemo, useState } from "react";
import {
    createUser,
    deleteUser,
    getUsers,
    updateUser,
} from "../api/api";

interface User {
    id: number;
    name: string;
    email: string;
    passwordHash: string;
    role: string;
    createdAt: string;
}

const Users = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [search, setSearch] = useState("");

    const [showModal, setShowModal] = useState(false);
    const [editingUser, setEditingUser] =
        useState<User | null>(null);

    const [form, setForm] = useState({
        name: "",
        email: "",
        passwordHash: "",
        role: "user",
    });

    /* =========================================================
       LOAD
       ========================================================= */

    const loadUsers = async () => {
        try {
            const res = await getUsers();

            setUsers(res.data);
        } catch (e) {
            console.error(e);
        }
    };

    useEffect(() => {
        loadUsers();
    }, []);

    /* =========================================================
       FILTER
       ========================================================= */

    const filteredUsers = useMemo(() => {
        return users.filter((u) => {
            const value = search.toLowerCase();

            return (
                u.name.toLowerCase().includes(value) ||
                u.email.toLowerCase().includes(value) ||
                u.role.toLowerCase().includes(value)
            );
        });
    }, [users, search]);

    /* =========================================================
       CREATE
       ========================================================= */

    const handleCreate = async () => {
        try {
            await createUser(form);

            setForm({
                name: "",
                email: "",
                passwordHash: "",
                role: "user",
            });

            setShowModal(false);

            await loadUsers();
        } catch (e) {
            console.error(e);
        }
    };

    /* =========================================================
       EDIT
       ========================================================= */

    const openEdit = (user: User) => {
        setEditingUser(user);

        setForm({
            name: user.name,
            email: user.email,
            passwordHash: user.passwordHash,
            role: user.role,
        });

        setShowModal(true);
    };

    const handleSave = async () => {
        if (!editingUser) return;

        try {
            await updateUser(
                editingUser.id,
                form
            );

            setEditingUser(null);

            setShowModal(false);

            await loadUsers();
        } catch (e) {
            console.error(e);
        }
    };

    /* =========================================================
       DELETE
       ========================================================= */

    const handleDelete = async (id: number) => {
        const ok = confirm(
            "Delete this user?"
        );

        if (!ok) return;

        try {
            await deleteUser(id);

            await loadUsers();
        } catch (e) {
            console.error(e);
        }
    };

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
                    "linear-gradient(to bottom, #020b25, #021540)",
            }}
        >
            {/* HEADER */}

            <div
                style={{
                    display: "flex",
                    justifyContent:
                        "space-between",
                    alignItems: "center",
                    marginBottom: "30px",
                }}
            >
                <h1
                    style={{
                        fontSize: "64px",
                        margin: 0,
                        fontWeight: 700,
                    }}
                >
                    👤 Users
                </h1>

                <button
                    onClick={() => {
                        setEditingUser(null);

                        setForm({
                            name: "",
                            email: "",
                            passwordHash: "",
                            role: "user",
                        });

                        setShowModal(true);
                    }}
                    style={{
                        padding:
                            "18px 36px",
                        borderRadius: "20px",
                        border: "none",
                        background:
                            "#3b82f6",
                        color: "white",
                        fontSize: "24px",
                        fontWeight: 700,
                        cursor: "pointer",
                    }}
                >
                    + Add User
                </button>
            </div>

            {/* SEARCH */}

            <input
                value={search}
                onChange={(e) =>
                    setSearch(
                        e.target.value
                    )
                }
                placeholder="Search users..."
                style={{
                    width: "420px",
                    padding: "20px",
                    borderRadius: "18px",
                    border: "none",
                    marginBottom: "30px",
                    background:
                        "#13254d",
                    color: "white",
                    fontSize: "20px",
                    outline: "none",
                }}
            />

            {/* USERS */}

            <div
                style={{
                    display: "flex",
                    flexDirection: "column",
                    gap: "20px",
                }}
            >
                {filteredUsers.length ===
                    0 && (
                        <p
                            style={{
                                fontSize:
                                    "28px",
                                opacity: 0.8,
                            }}
                        >
                            No users found
                        </p>
                    )}

                {filteredUsers.map((u) => (
                    <div
                        key={u.id}
                        style={{
                            background:
                                "#08152f",
                            borderRadius:
                                "24px",
                            padding: "24px",
                            display:
                                "flex",
                            justifyContent:
                                "space-between",
                            alignItems:
                                "center",
                        }}
                    >
                        <div>
                            <h2
                                style={{
                                    margin:
                                        0,
                                    marginBottom:
                                        "12px",
                                }}
                            >
                                {u.name}
                            </h2>

                            <div
                                style={{
                                    opacity:
                                        0.8,
                                    marginBottom:
                                        "8px",
                                }}
                            >
                                📧{" "}
                                {
                                    u.email
                                }
                            </div>

                            <div
                                style={{
                                    opacity:
                                        0.8,
                                    marginBottom:
                                        "8px",
                                }}
                            >
                                🔑{" "}
                                {
                                    u.passwordHash
                                }
                            </div>

                            <div
                                style={{
                                    display:
                                        "inline-block",
                                    padding:
                                        "6px 14px",
                                    borderRadius:
                                        "12px",
                                    background:
                                        u.role ===
                                        "admin"
                                            ? "#ef4444"
                                            : "#22c55e",
                                    fontWeight: 700,
                                }}
                            >
                                {u.role}
                            </div>
                        </div>

                        <div
                            style={{
                                display:
                                    "flex",
                                gap: "16px",
                            }}
                        >
                            <button
                                onClick={() =>
                                    openEdit(
                                        u
                                    )
                                }
                                style={{
                                    background:
                                        "transparent",
                                    border:
                                        "none",
                                    color: "#facc15",
                                    fontSize:
                                        "24px",
                                    cursor:
                                        "pointer",
                                    fontWeight: 700,
                                }}
                            >
                                Edit
                            </button>

                            <button
                                onClick={() =>
                                    handleDelete(
                                        u.id
                                    )
                                }
                                style={{
                                    background:
                                        "transparent",
                                    border:
                                        "none",
                                    color: "#ef4444",
                                    fontSize:
                                        "24px",
                                    cursor:
                                        "pointer",
                                    fontWeight: 700,
                                }}
                            >
                                Delete
                            </button>
                        </div>
                    </div>
                ))}
            </div>

            {/* MODAL */}

            {showModal && (
                <div
                    style={{
                        position: "fixed",
                        inset: 0,
                        background:
                            "rgba(0,0,0,0.5)",
                        display: "flex",
                        justifyContent:
                            "center",
                        alignItems:
                            "center",
                        zIndex: 999,
                    }}
                >
                    <div
                        style={{
                            width: "520px",
                            background:
                                "#13254d",
                            borderRadius:
                                "28px",
                            padding: "36px",
                        }}
                    >
                        <h2
                            style={{
                                marginTop: 0,
                                marginBottom:
                                    "24px",
                            }}
                        >
                            {editingUser
                                ? "Edit User"
                                : "Add New User"}
                        </h2>

                        <div
                            style={{
                                display:
                                    "flex",
                                flexDirection:
                                    "column",
                                gap: "18px",
                            }}
                        >
                            <input
                                placeholder="Name"
                                value={
                                    form.name
                                }
                                onChange={(
                                    e
                                ) =>
                                    setForm(
                                        {
                                            ...form,
                                            name: e
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
                                placeholder="Email"
                                value={
                                    form.email
                                }
                                onChange={(
                                    e
                                ) =>
                                    setForm(
                                        {
                                            ...form,
                                            email:
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
                                placeholder="Password Hash"
                                value={
                                    form.passwordHash
                                }
                                onChange={(
                                    e
                                ) =>
                                    setForm(
                                        {
                                            ...form,
                                            passwordHash:
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

                            <select
                                value={
                                    form.role
                                }
                                onChange={(
                                    e
                                ) =>
                                    setForm(
                                        {
                                            ...form,
                                            role: e
                                                .target
                                                .value,
                                        }
                                    )
                                }
                                style={
                                    inputStyle
                                }
                            >
                                <option value="user">
                                    user
                                </option>

                                <option value="admin">
                                    admin
                                </option>
                            </select>
                        </div>

                        <div
                            style={{
                                display:
                                    "flex",
                                justifyContent:
                                    "flex-end",
                                gap: "16px",
                                marginTop:
                                    "32px",
                            }}
                        >
                            <button
                                onClick={() =>
                                    setShowModal(
                                        false
                                    )
                                }
                                style={{
                                    padding:
                                        "14px 26px",
                                    borderRadius:
                                        "16px",
                                    border:
                                        "none",
                                    background:
                                        "#475569",
                                    color: "white",
                                    cursor:
                                        "pointer",
                                    fontSize:
                                        "18px",
                                }}
                            >
                                Cancel
                            </button>

                            <button
                                onClick={
                                    editingUser
                                        ? handleSave
                                        : handleCreate
                                }
                                style={{
                                    padding:
                                        "14px 26px",
                                    borderRadius:
                                        "16px",
                                    border:
                                        "none",
                                    background:
                                        "#3b82f6",
                                    color: "white",
                                    cursor:
                                        "pointer",
                                    fontSize:
                                        "18px",
                                    fontWeight: 700,
                                }}
                            >
                                {editingUser
                                    ? "Save"
                                    : "Create"}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

const inputStyle = {
    padding: "18px",
    borderRadius: "16px",
    border: "none",
    background: "#08152f",
    color: "white",
    fontSize: "18px",
    outline: "none",
};

export default Users;