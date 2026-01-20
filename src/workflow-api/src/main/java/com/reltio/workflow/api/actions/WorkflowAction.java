package com.reltio.workflow.api.actions;

/**
 * This interface should be implemented for creating action classes for service tasks
 */
public interface WorkflowAction {
	/**
	 * Implement this method with specific behavior
	 */
    void execute(Execution execution) throws Exception;
}
