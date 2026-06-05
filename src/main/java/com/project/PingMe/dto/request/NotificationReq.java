package com.project.PingMe.dto.request;

import com.project.PingMe.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationReq {
    private String message;
    private String recipient;
    private Channel channel;

}
