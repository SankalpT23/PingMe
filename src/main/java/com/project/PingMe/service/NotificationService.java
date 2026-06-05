package com.project.PingMe.service;

import com.project.PingMe.dto.request.NotificationReq;
import com.project.PingMe.dto.response.NotificationRes;
import com.project.PingMe.enums.Channel;
import com.project.PingMe.enums.NotificationStatus;
import com.project.PingMe.model.NotificationLog;
import com.project.PingMe.repository.NotificationLogRepo;
import com.project.PingMe.strategy.NotificationChannel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    @Autowired
    private NotificationLogRepo notificationLogRepo;
    @Autowired
    private AsyncNotificationWorker asyncNotificationWorker;

    @Autowired
    private List<NotificationChannel> channels;

    private final Map<Channel, NotificationChannel> channelMap = new EnumMap<>(Channel.class);


    @PostConstruct
    public void  init()
    {
        for (NotificationChannel channel : channels)
        {
            channelMap.put(channel.getType(), channel);
        }
    }



    public NotificationRes processNotification(NotificationReq notificationReq) {
        NotificationLog log = new NotificationLog();
        log.setChannel(notificationReq.getChannel());
        log.setMessage(notificationReq.getMessage());
        log.setRecipient(notificationReq.getRecipient());
        log.setStatus(NotificationStatus.PENDING);

        NotificationLog savedLog = notificationLogRepo.save(log);
        asyncNotificationWorker.sendAsync(notificationReq,savedLog,channelMap);

        return new NotificationRes(savedLog.getId(), savedLog.getStatus());
    }


}
