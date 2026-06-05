package com.project.PingMe.dto.response;

import com.project.PingMe.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRes {
    private Long notificationId;
    private NotificationStatus status;
}
