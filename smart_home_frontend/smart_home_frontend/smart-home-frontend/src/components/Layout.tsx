import { Link } from "react-router-dom";

export default function Layout({ children }: any) {
    return (
        <div className="min-h-screen bg-gray-900">
            <nav className="bg-gray-800 p-4 flex gap-6 text-white">
                <Link to="/">Homes</Link>
                <Link to="/dashboard">Dashboard</Link>
                <Link to="/logs">Logs</Link>
                <Link to="/users">Users</Link>
                <Link to="/rules">Rules</Link>
                <Link to="/rules/new">+ Rule</Link>

            </nav>

            {children}
        </div>
    );
}