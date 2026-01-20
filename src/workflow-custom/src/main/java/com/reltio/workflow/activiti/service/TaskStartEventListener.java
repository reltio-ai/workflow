package com.reltio.workflow.activiti.service;

import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.listeners.WorkflowTaskListener;
import com.reltio.workflow.api.tasks.Task;
import com.reltio.workflow.api.tasks.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Class to override task start listener
 */
public class TaskStartEventListener implements WorkflowTaskListener {
    public static final Logger logger = LoggerFactory.getLogger(TaskStartEventListener.class);
    @WorkflowService
    private TaskService taskService;

    public void notify(final Task task) {
        String reltioUser = task.getReltioUser();
		// Change owner of task
        taskService.setOwner(task, reltioUser);

        String assignee = task.getAssignee();
        if (assignee != null) {
            // assign to the predefined user only if it has necessary permissions
            Set<String> possibleAssignees = taskService.getAllPossibleAssignees(task);
            if (!possibleAssignees.contains(assignee)) {
                taskService.assignTask(task, null);
            } else {    // generate update event anyway
                taskService.assignTask(task, assignee);
            }
        } else {
            // Choose possible assignee if task assignee is null
            assignee = taskService.getPossibleAssignee(task);
            if (assignee != null) {
                taskService.assignTask(task, assignee);
            }
        }
    }
}