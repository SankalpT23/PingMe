import { useState } from 'react';
import { sendNotification } from '../../services/api.js';
import './NotificationForm.css';

function NotificationForm({ onNotificationSent }) {
  const [formData, setFormData] = useState({
    recipient: '',
    channel: 'EMAIL',
    message: '',
  });
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState(null);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus(null);

    try {
      const res = await sendNotification(formData);
      setStatus({
        type: 'success',
        message: `Notification #${res.data.notificationId} queued — Status: ${res.data.status}`,
      });
      if (onNotificationSent) {
        onNotificationSent(res.data);
      }
      setFormData({ ...formData, message: '' });
    } catch (err) {
      const msg =
        err.response?.data || err.response?.statusText || 'Failed to send notification';
      setStatus({
        type: 'error',
        message: typeof msg === 'string' ? msg : JSON.stringify(msg),
      });
    } finally {
      setLoading(false);
      setTimeout(() => setStatus(null), 5000);
    }
  };

  const channels = [
    { value: 'EMAIL', label: 'Email', icon: '📧' },
    { value: 'SMS', label: 'SMS', icon: '📱' },
    { value: 'WEBSOCKET', label: 'WebSocket', icon: '🔔' },
  ];

  return (
    <div className="notification-form-card glass animate-fade-in" id="notification-form">
      <div className="form-header">
        <h2 className="form-title">
          <span className="form-icon">🚀</span>
          Send Notification
        </h2>
        <p className="form-subtitle">
          AI will enhance your message into a professional alert
        </p>
      </div>

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label" htmlFor="recipient-input">Recipient</label>
          <input
            id="recipient-input"
            type="text"
            name="recipient"
            className="form-input"
            placeholder="e.g. user@example.com"
            value={formData.recipient}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label className="form-label">Channel</label>
          <div className="channel-selector" id="channel-selector">
            {channels.map((ch) => (
              <button
                key={ch.value}
                type="button"
                className={`channel-btn ${formData.channel === ch.value ? 'active' : ''}`}
                onClick={() => setFormData({ ...formData, channel: ch.value })}
                id={`channel-${ch.value.toLowerCase()}`}
              >
                <span className="channel-icon">{ch.icon}</span>
                <span className="channel-label">{ch.label}</span>
              </button>
            ))}
          </div>
        </div>

        <div className="form-group">
          <label className="form-label" htmlFor="message-input">Message</label>
          <textarea
            id="message-input"
            name="message"
            className="form-textarea"
            placeholder="Type your raw message... AI will make it professional ✨"
            rows="4"
            value={formData.message}
            onChange={handleChange}
            required
          />
        </div>

        <button
          type="submit"
          className="btn-send"
          id="send-notification-button"
          disabled={loading}
        >
          {loading ? (
            <>
              <span className="spinner" />
              Sending...
            </>
          ) : (
            <>
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <line x1="22" y1="2" x2="11" y2="13" />
                <polygon points="22 2 15 22 11 13 2 9 22 2" />
              </svg>
              Send Notification
            </>
          )}
        </button>
      </form>

      {status && (
        <div className={`status-toast ${status.type} animate-slide-in`} id="notification-status">
          <span className="toast-icon">
            {status.type === 'success' ? '✅' : '❌'}
          </span>
          {status.message}
        </div>
      )}
    </div>
  );
}

export default NotificationForm;
