import axios from "axios";

/* =========================================================
   AXIOS INSTANCE
   ========================================================= */

const API = axios.create({
    baseURL: "http://localhost:8080/api",
});

/* =========================================================
   HOMES
   ========================================================= */

export const getHomes = () =>
    API.get("/homes");

export const createHome = (data: any) =>
    API.post("/homes", data);

export const deleteHome = (id: number) =>
    API.delete(`/homes/${id}`);

export const getHomeLogs = (homeId: number) =>
    API.get(`/homes/${homeId}/logs/details`);

/* =========================================================
   ROOMS
   ========================================================= */

export const getRooms = (homeId: number) =>
    API.get(`/homes/${homeId}/rooms`);

export const createRoom = (data: any) =>
    API.post("/rooms", data);

export const deleteRoom = (id: number) =>
    API.delete(`/rooms/${id}`);

/* =========================================================
   DEVICES
   ========================================================= */

// ✅ backend endpoint:
// GET /api/devices/room/{roomId}

export const getDevices = (roomId: number) =>
    API.get(`/devices/room/${roomId}`);

export const createDevice = (data: any) =>
    API.post("/devices", data);

export const deleteDevice = (id: number) =>
    API.delete(`/devices/${id}`);

export const toggleDevice = (id: number) =>
    API.post(`/devices/${id}/toggle`);

export const testDevice = (id: number) =>
    API.post(`/devices/${id}/test`);

/* =========================================================
   DASHBOARD
   ========================================================= */

export const getDashboard = async (
    homeId: number = 1
) => {

    const res = await API.get("/dashboard", {
        params: { homeId },
    });

    return res.data;
};

/* =========================================================
   LOGS
   ========================================================= */

export const getLogs = () =>
    API.get("/logs");

// ✅ сразу возвращаем массив логов
export const getReadableLogs = async () => {
    const res = await API.get("/logs");
    return res.data;
};

/* =========================================================
   EVENTS
   ========================================================= */

export const triggerEvent = (
    sensorId: number,
    value: number
) =>
    API.get(`/events/trigger/${sensorId}/${value}`);

export const processEvents = () =>
    API.get("/events/process");

export const getSensors = () =>
    API.get("/sensors");

/* =========================================================
   SYSTEM
   ========================================================= */

export const getDependencyGraph = () =>
    API.get("/dependency-graph");

export const getSummary = () =>
    API.get("/summary");

export const addDependency = (
    from: number,
    to: number
) =>
    API.get(`/dependencies/add/${from}/${to}`);


/* =========================================================
   UPDATE
   ========================================================= */
export const updateHome = (
    id: number,
    data: any
) =>
    API.put(`/edit/homes/${id}`, data);

export const updateRoom = (
    id: number,
    data: any
) =>
    API.put(`/edit/rooms/${id}`, data);

/* =========================================================
   USERS
   ========================================================= */

export const getUsers = () =>
    API.get("/users");

export const createUser = (data: any) =>
    API.post("/users", data);

export const updateUser = (
    id: number,
    data: any
) =>
    API.put(`/users/${id}`, data);

export const deleteUser = (id: number) =>
    API.delete(`/users/${id}`);

/* =========================================================
   RULES
   ========================================================= */

export const getRules = () =>
    API.get("/rules");

export const getRuleById = (
    id: number
) =>
    API.get(`/rules/${id}`);

export const getRulesByHome = (
    homeId: number
) =>
    API.get(`/rules/home/${homeId}`);

export const createRule = (
    data: any
) =>
    API.post("/rules", data);

export const updateRule = (
    id: number,
    data: any
) =>
    API.put(`/rules/${id}`, data);

export const deleteRule = (
    id: number
) =>
    API.delete(`/rules/${id}`);

export const enableRule = (
    id: number
) =>
    API.put(`/rules/${id}/enable`);

export const disableRule = (
    id: number
) =>
    API.put(`/rules/${id}/disable`);


