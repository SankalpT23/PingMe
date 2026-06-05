package com.project.PingMe.repository;

import com.project.PingMe.model.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepo extends JpaRepository<NotificationLog, Long> {
}
