package com.project.PingMe.strategy;

import com.project.PingMe.enums.Channel;
import org.springframework.stereotype.Component;

@Component
public class EmailChannel implements NotificationChannel {

    @Override
    public void send(String recipient, String subject, String message) {
        System.out.println("Sending email to " + recipient);
    }

    @Override
    public Channel getType() {
        return Channel.EMAIL;
    }
}
