package com.project.PingMe.strategy;

import com.project.PingMe.enums.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebsocketChannel implements NotificationChannel{

    //Pushes The message through the Websocket pipe
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void send(String recipient, String subject, String message) {
        String destination = "/topic/notifications/" + recipient;
        simpMessagingTemplate.convertAndSend(destination, message);

        System.out.println("Live Websocket Push Triggered for: " + recipient);
    }

    @Override
    public Channel getType() {
        return Channel.WEBSOCKET;
    }
}
