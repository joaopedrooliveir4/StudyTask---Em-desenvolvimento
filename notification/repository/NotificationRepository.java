package com.joaopedro.studytask.notification.repository;

import com.joaopedro.studytask.notification.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

    List<NotificationModel> findByVisualizadaFalse();

    List<NotificationModel> findByTaskId(Long taskId);
}