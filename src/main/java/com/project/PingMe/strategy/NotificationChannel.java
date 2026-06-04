package com.project.PingMe.strategy;

import com.project.PingMe.enums.Channel;

public interface NotificationChannel {
    //Sends The Actual Message
    public void send(String recipient, String subject, String message);
    Channel getType();
}
