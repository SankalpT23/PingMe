import SockJS from 'sockjs-client/dist/sockjs';
import { Client } from '@stomp/stompjs';

const WS_URL = 'http://localhost:8080/ws';

export function connectWebSocket(recipient, onMessage) {
  const client = new Client({
    webSocketFactory: () => new SockJS(WS_URL),
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    debug: () => {},
    onConnect: () => {
      console.log('[PingMe WS] Connected');
      client.subscribe(`/topic/notifications/${recipient}`, (message) => {
        if (message.body) {
          onMessage(message.body);
        }
      });
    },
    onStompError: (frame) => {
      console.error('[PingMe WS] STOMP error:', frame.headers['message']);
    },
    onDisconnect: () => {
      console.log('[PingMe WS] Disconnected');
    },
  });

  client.activate();
  return client;
}

export function disconnectWebSocket(client) {
  if (client && client.active) {
    client.deactivate();
  }
}
