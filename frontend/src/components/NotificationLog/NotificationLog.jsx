import './NotificationLog.css';

function NotificationLog({ logs }) {
  const getStatusBadge = (status) => {
    const map = {
      PENDING: { class: 'badge-warning', icon: '⏳' },
      SENT: { class: 'badge-success', icon: '✅' },
      FAILED: { class: 'badge-error', icon: '❌' },
    };
    return map[status] || { class: 'badge-info', icon: '📌' };
  };

  const getChannelIcon = (channel) => {
    const map = {
      EMAIL: '📧',
      SMS: '📱',
      WEBSOCKET: '🔔',
    };
    return map[channel] || '📨';
  };

  return (
    <div className="log-card glass animate-fade-in" id="notification-log">
      <div className="log-header">
        <h2 className="log-title">
          <span className="log-icon">📊</span>
          Activity Log
        </h2>
        <span className="log-count">{logs.length} notification{logs.length !== 1 ? 's' : ''}</span>
      </div>

      {logs.length === 0 ? (
        <div className="log-empty">
          <p className="empty-text">No notifications sent yet</p>
        </div>
      ) : (
        <div className="log-table-wrapper">
          <table className="log-table" id="notification-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Channel</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {logs.map((log, i) => {
                const badge = getStatusBadge(log.status);
                return (
                  <tr key={log.notificationId || i} className="animate-fade-in" style={{ animationDelay: `${i * 0.05}s` }}>
                    <td>
                      <span className="log-id">#{log.notificationId}</span>
                    </td>
                    <td>
                      <span className="channel-tag">
                        {getChannelIcon(log.channel)}
                      </span>
                    </td>
                    <td>
                      <span className={`status-badge ${badge.class}`}>
                        {badge.icon} {log.status}
                      </span>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default NotificationLog;
