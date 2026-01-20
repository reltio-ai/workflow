package com.reltio.workflow.api.notification.mail;

import com.reltio.workflow.api.tasks.Task;

/**
 * Service that sends notifications
 */
public interface NotificationSenderService {
    /**
     * Sends a notification
     * @param notification notification to send
     */
    void sendNotification(Notification notification);

    /**
     * Resolves an email address for a task owner
     *
     * @param task a task object
     * @return Returns an email address or {@code null} if it cannot be resolved.
     */
    String resolveTaskOwnerEmail(Task task);

    /**
     * Resolves an email address for a task assignee
     *
     * @param task a task object
     * @return Returns an email address or {@code null} if it cannot be resolved or no assignee for the task.
     */
    String resolveTaskAssigneeEmail(Task task);
}
