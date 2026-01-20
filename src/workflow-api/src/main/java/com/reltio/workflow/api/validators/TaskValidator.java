package com.reltio.workflow.api.validators;

import com.reltio.workflow.api.tasks.Task;

/**
 * Implement this interface if you want to mark incorrect task by specific validation rules
 * After marking it will be possible to terminate invalid process instances via REST API
 * Use @{@link com.reltio.workflow.api.WorkflowService} to inject task service and others
 */
public interface TaskValidator {
	/**
	 * Implement this method with specific validation rules, throw @{@link ValidationError} with validation message if
	 * task is not correct
	 */
    void doValidate(Task taskInfo) throws Exception;
}
