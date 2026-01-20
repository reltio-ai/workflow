package com.reltio.workflow.api.tasks;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Service to get data/ updata task
 */
public interface TaskService {
	/**
	 * Change assignee of specific task
	 */
	Set<String> getAllPossibleAssignees(Task task);

	/**
	 * Change assignee of specific task
	 */
    void assignTask(Task task, String assignee);

	/**
	 * Change owner of specific task
	 */
    void setOwner(Task task, String reltioUser);

	/**
	 * Get possible assignee of this task
	 */
    String getPossibleAssignee(Task task);

	/**
	 * Get process definition key of specific task
	 */
    String getProcessDefinitionKey(Task task);

	/**
	 * Get boolean form property of specific task
	 */
    boolean getBooleanFormProperty(Task task, String property);

	/**
	 * Get string form property of specific task
	 */
    String getStringFormProperty(Task task, String property);

	/**
	 * Returns candidates of specific task
	 */
    Candidates getCandidates(Task task);

	/**
	 * set variable on a task. If the variable is not already existing, it will be created in the most outer scope. This
	 * means the process instance in case this task is related to an execution.
	 */
	void setVariable(String taskId, String variableName, Object value);

	/**
	 * set variables on a task. If the variable is not already existing, it will be created in the most outer scope.
	 * This means the process instance in case this task is related to an execution.
	 */
	void setVariables(String taskId, Map<String, ? extends Object> variables);

	/**
	 * set variable on a task. If the variable is not already existing, it will be created in the task.
	 */
	void setVariableLocal(String taskId, String variableName, Object value);

	/**
	 * set variables on a task. If the variable is not already existing, it will be created in the task.
	 */
	void setVariablesLocal(String taskId, Map<String, ? extends Object> variables);

	/** get a variables and search in the task scope and if available also the execution scopes. */
	Object getVariable(String taskId, String variableName);

	/** get a variables and search in the task scope and if available also the execution scopes. */
	<T> T getVariable(String taskId, String variableName, Class<T> variableClass);

	/**
	 * checks whether or not the task has a variable defined with the given name, in the task scope and if available
	 * also the execution scopes.
	 */
	boolean hasVariable(String taskId, String variableName);

	/** checks whether or not the task has a variable defined with the given name. */
	Object getVariableLocal(String taskId, String variableName);

	/** checks whether or not the task has a variable defined with the given name. */
	<T> T getVariableLocal(String taskId, String variableName, Class<T> variableClass);

	/** checks whether or not the task has a variable defined with the given name, local task scope only. */
	boolean hasVariableLocal(String taskId, String variableName);

	/**
	 * get all variables and search in the task scope and if available also the execution scopes. If you have many
	 * variables and you only need a few, consider using {@link #getVariables(String, Collection)} for better
	 * performance.
	 */
	Map<String, Object> getVariables(String taskId);

	/**
	 * get all variables and search only in the task scope. If you have many task local variables and you only need a
	 * few, consider using {@link #getVariablesLocal(String, Collection)} for better performance.
	 */
	Map<String, Object> getVariablesLocal(String taskId);

	/** get values for all given variableNames and search only in the task scope. */
	Map<String, Object> getVariables(String taskId, Collection<String> variableNames);

	/** get a variable on a task */
	Map<String, Object> getVariablesLocal(String taskId, Collection<String> variableNames);

	/**
	 * Removes the variable from the task. When the variable does not exist, nothing happens.
	 */
	void removeVariable(String taskId, String variableName);

	/**
	 * Removes the variable from the task (not considering parent scopes). When the variable does not exist, nothing
	 * happens.
	 */
	void removeVariableLocal(String taskId, String variableName);

	/**
	 * Removes all variables in the given collection from the task. Non existing variable names are simply ignored.
	 */
	void removeVariables(String taskId, Collection<String> variableNames);

	/**
	 * Removes all variables in the given collection from the task (not considering parent scopes). Non existing
	 * variable names are simply ignored.
	 */
	void removeVariablesLocal(String taskId, Collection<String> variableNames);

	/**
	 * Changes the due date of the task
	 */
	void setDueDate(String taskId, Date dueDate);

	/**
	 * Changes the priority of the task
	 */
	void setPriority(String taskId, int priority);
}
