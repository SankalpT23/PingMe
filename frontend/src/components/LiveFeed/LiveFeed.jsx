import { useState, useEffect, useRef } from 'react';
import { useAuth } from '../../context/AuthContext.jsx';
import { connectWebSocket, disconnectWebSocket } from '../../services/websocket.js';
import './LiveFeed.css';

function LiveFeed() {
  const { user } = useAuth();
  const [messages, setMessages] = useState([]);
  const [connected, setConnected] = useState(false);
  const clientRef = useRef(null);
  const feedRef = useRef(null);

  useEffect(() => {
    if (!user?.email) return;

    const client = connectWebSocket(user.email, (msg) => {
      const newMessage = {
        id: Date.now(),
        text: msg,
        timestamp: new Date().toLocaleTimeString(),
      };
      setMessages((prev) => [newMessage, ...prev].slice(0, 50));
    });

    clientRef.current = client;

    // Check connection after a brief delay
    const timer = setTimeout(() => setConnected(true), 1500);

    return () => {
      clearTimeout(timer);
      disconnectWebSocket(client);
      setConnected(false);
    };
  }, [user?.email]);

  return (
    <div className="live-feed-card glass animate-fade-in" id="live-feed">
      <div className="feed-header">
        <div className="feed-title-row">
          <h2 className="feed-title">
            <span className="feed-icon">🔔</span>
            Live Feed
          </h2>
          <div className={`connection-status ${connected ? 'online' : 'offline'}`} id="ws-status">
            <span className="status-dot" />
            <span className="status-text">{connected ? 'Connected' : 'Connecting...'}</span>
          </div>
        </div>
        <p className="feed-subtitle">
          Real-time WebSocket notifications for <strong>{user?.email || 'you'}</strong>
        </p>
      </div>

      <div className="feed-body" ref={feedRef}>
        {messages.length === 0 ? (
          <div className="feed-empty" id="feed-empty-state">
            <div className="empty-icon">📡</div>
            <p className="empty-text">Waiting for live notifications...</p>
            <p className="empty-hint">Send a WEBSOCKET notification to see it here in real-time</p>
          </div>
        ) : (
          <div className="feed-list">
            {messages.map((msg) => (
              <div key={msg.id} className="feed-item animate-slide-in">
                <div className="feed-item-indicator" />
                <div className="feed-item-content">
                  <p className="feed-item-text">{msg.text}</p>
                  <span className="feed-item-time">{msg.timestamp}</span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default LiveFeed;
