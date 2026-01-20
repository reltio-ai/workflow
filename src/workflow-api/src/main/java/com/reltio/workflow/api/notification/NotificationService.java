package com.reltio.workflow.api.notification;

import com.reltio.workflow.api.tasks.Task;

/**
 * Will send notifications by specific event
 */
public interface NotificationService {
    void sendNotification(NotificationEvent notificationEvent, Task delegateTask);
}
