package com.project.PingMe.controller;

import com.project.PingMe.dto.request.NotificationReq;
import com.project.PingMe.dto.response.NotificationRes;
import com.project.PingMe.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationRes> notify(@RequestBody NotificationReq notificationReq){
        NotificationRes res = notificationService.processNotification(notificationReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
