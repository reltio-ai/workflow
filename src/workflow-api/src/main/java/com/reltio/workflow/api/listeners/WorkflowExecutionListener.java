package com.reltio.workflow.api.listeners;

import com.reltio.workflow.api.actions.Execution;

/**
 * Implement this interface for create listeners of specific service tasks
 * Use @{@link com.reltio.workflow.api.WorkflowService} to inject execution service and others
 */
public interface WorkflowExecutionListener {
	/**
	 * Override this method with specific listener behavior
	 * 
	 * @param execution
	 */
    void notify(Execution execution);
}
