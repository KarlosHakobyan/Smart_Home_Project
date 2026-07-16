import { Routes, Route, Navigate } from "react-router-dom";

import Homes from "./pages/Homes";
import Rooms from "./pages/Rooms";
import Devices from "./pages/Devices";
import Dashboard from "./pages/Dashboard";
import { Logs } from "./pages/Logs";
import RuleBuilder from "./pages/RuleBuilder";
import Users from "./pages/User";
import Rules from "./pages/Rules";

import Layout from "./components/Layout";

function App() {
    return (
        <Routes>

            <Route
                path="/"
                element={
                    <Layout>
                        <Homes />
                    </Layout>
                }
            />

            <Route
                path="/homes"
                element={
                    <Layout>
                        <Homes />
                    </Layout>
                }
            />

            <Route
                path="/rooms/:homeId"
                element={
                    <Layout>
                        <Rooms />
                    </Layout>
                }
            />

            <Route
                path="/devices/:roomId"
                element={
                    <Layout>
                        <Devices />
                    </Layout>
                }
            />

            <Route
                path="/dashboard"
                element={
                    <Layout>
                        <Dashboard />
                    </Layout>
                }
            />

            <Route
                path="/logs"
                element={
                    <Layout>
                        <Logs />
                    </Layout>
                }
            />

            <Route
                path="/rules"
                element={
                    <Layout>
                        <Rules />
                    </Layout>
                }
            />

            <Route
                path="/rules/new"
                element={
                    <Layout>
                        <RuleBuilder />
                    </Layout>
                }
            />

            <Route
                path="/users"
                element={
                    <Layout>
                        <Users />
                    </Layout>
                }
            />

            <Route
                path="*"
                element={<Navigate to="/" replace />}
            />

        </Routes>
    );
}

export default App;