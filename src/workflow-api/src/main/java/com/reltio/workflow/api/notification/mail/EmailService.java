package com.reltio.workflow.api.notification.mail;

import com.reltio.workflow.api.tasks.Task;

/**
 * Service to send email messages
 */
public interface EmailService {
    /**
     * Sends an email
     *
     * @param email sending email
     */
    void sendEmail(Email email);

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
