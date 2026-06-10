package com.project.PingMe.strategy;

import com.project.PingMe.enums.Channel;

public class SmsChannel implements NotificationChannel {
    @Override
    public void send(String recipient, String subject, String message) {
        System.out.println("Sending email to " + recipient);
    }

    @Override
    public Channel getType() {
        return Channel.SMS;
    }


}
