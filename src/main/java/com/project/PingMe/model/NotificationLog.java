package com.project.PingMe.model;

import com.project.PingMe.enums.Channel;
import com.project.PingMe.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NotificationLog")
public class NotificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String recipient;

    @Enumerated(EnumType.STRING)
    private Channel channel;
    @Enumerated(EnumType.STRING)
    private NotificationStatus  status;
}
