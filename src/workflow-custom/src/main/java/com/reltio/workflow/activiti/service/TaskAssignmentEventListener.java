package com.reltio.workflow.activiti.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.listeners.WorkflowTaskListener;
import com.reltio.workflow.api.notification.NotificationEvent;
import com.reltio.workflow.api.notification.NotificationService;
import com.reltio.workflow.api.tasks.Task;
import com.reltio.workflow.api.tasks.TaskConstants;
import com.reltio.workflow.api.tasks.TaskService;

/**
 * Listener class to be called when a task is assigned/reassigned
 */
public class TaskAssignmentEventListener implements WorkflowTaskListener {
    public static final Logger logger = LoggerFactory.getLogger(TaskAssignmentEventListener.class);
	// Injectected notification service
    @WorkflowService
    private NotificationService notificationService;

    @WorkflowService
    private TaskService taskService;

    public void notify(Task task) {
		// Check form property
        if (taskService.getBooleanFormProperty(task, TaskConstants.NOTIFICATION_ON_ASSIGNMENT_ENABLED)) {
            notificationService.sendNotification(NotificationEvent.ASSIGN, task);
        }
    }
}
