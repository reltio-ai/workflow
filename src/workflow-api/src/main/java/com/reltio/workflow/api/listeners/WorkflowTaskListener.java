package com.reltio.workflow.api.listeners;

import com.reltio.workflow.api.tasks.Task;

/**
 * Implement this interface for create listeners of specific user tasks
 * Use @{@link com.reltio.workflow.api.WorkflowService} to inject task service and others
 */
public interface WorkflowTaskListener {
	/**
	 * Override this method with specific listener behavior
	 */
    void notify(Task task);
}
