package com.reltio.workflow.api.validators;

import com.reltio.workflow.api.tasks.Task;

import java.util.List;
import java.util.Map;

/**
 * Implement this interface if you want to mark multiple incorrect tasks by specific validation rules at once
 * After marking it will be possible to terminate invalid process instances via REST API
 * Use @{@link com.reltio.workflow.api.WorkflowService} to inject task service and others
 */
public interface MultipleTasksValidator {
	/**
	 * Implement this method with specific validation rules, throw @{@link ValidationError} with validation message if
	 * task is not correct
	 */
	Map<Task, ValidationResult> doValidate(String environmentUrl, String tenant, String accessToken, List<Task> tasks) throws Exception;
}
