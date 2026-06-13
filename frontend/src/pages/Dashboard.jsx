import { useState } from 'react';
import Navbar from '../components/Navbar/Navbar.jsx';
import NotificationForm from '../components/NotificationForm/NotificationForm.jsx';
import LiveFeed from '../components/LiveFeed/LiveFeed.jsx';
import NotificationLog from '../components/NotificationLog/NotificationLog.jsx';
import './Dashboard.css';

function Dashboard() {
  const [logs, setLogs] = useState([]);

  const handleNotificationSent = (data) => {
    setLogs((prev) => [data, ...prev]);
  };

  return (
    <div className="dashboard" id="dashboard-page">
      <Navbar />

      <main className="dashboard-content">
        {/* ── Stats Bar ── */}
        <div className="stats-bar animate-fade-in" id="stats-bar">
          <div className="stat-card glass">
            <div className="stat-icon">📤</div>
            <div className="stat-info">
              <span className="stat-value">{logs.length}</span>
              <span className="stat-label">Total Sent</span>
            </div>
          </div>
          <div className="stat-card glass">
            <div className="stat-icon">⏳</div>
            <div className="stat-info">
              <span className="stat-value">
                {logs.filter((l) => l.status === 'PENDING').length}
              </span>
              <span className="stat-label">Pending</span>
            </div>
          </div>
          <div className="stat-card glass">
            <div className="stat-icon">✅</div>
            <div className="stat-info">
              <span className="stat-value">
                {logs.filter((l) => l.status === 'SENT').length}
              </span>
              <span className="stat-label">Delivered</span>
            </div>
          </div>
          <div className="stat-card glass">
            <div className="stat-icon">🛡️</div>
            <div className="stat-info">
              <span className="stat-value">10</span>
              <span className="stat-label">Rate Limit / min</span>
            </div>
          </div>
        </div>

        {/* ── Main Grid ── */}
        <div className="dashboard-grid">
          <div className="grid-left">
            <NotificationForm onNotificationSent={handleNotificationSent} />
            <NotificationLog logs={logs} />
          </div>
          <div className="grid-right">
            <LiveFeed />
          </div>
        </div>
      </main>
    </div>
  );
}

export default Dashboard;
