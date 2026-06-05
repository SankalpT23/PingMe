package com.project.PingMe.service;

import com.project.PingMe.dto.request.NotificationReq;
import com.project.PingMe.enums.Channel;
import com.project.PingMe.enums.NotificationStatus;
import com.project.PingMe.model.NotificationLog;
import com.project.PingMe.repository.NotificationLogRepo;
import com.project.PingMe.strategy.NotificationChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AsyncNotificationWorker {

    @Autowired
    private NotificationLogRepo repository;

    @Async
    public void sendAsync(NotificationReq notificationReq, NotificationLog savedLog, Map<Channel, NotificationChannel> channelMap ) {
        NotificationChannel channel = channelMap.get(notificationReq.getChannel());

        try{
            channel.send(notificationReq.getRecipient(),"PingMe Alert",notificationReq.getMessage());

            savedLog.setStatus(NotificationStatus.SENT);
            repository.save(savedLog);
        } catch (Exception e) {

            savedLog.setStatus(NotificationStatus.FAILED);
            repository.save(savedLog);
        }
    }

}
