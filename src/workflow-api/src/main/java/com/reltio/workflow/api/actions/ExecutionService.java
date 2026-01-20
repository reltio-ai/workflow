package com.reltio.workflow.api.actions;

import java.util.Map;
import java.util.Set;

/**
 * Interface for work with execution
 */
public interface ExecutionService
{
	/**
	 * Set variable value
	 */
	@Deprecated
	void setVariable(Execution execution, String name, Object value);

	/**
	 * Get variable value by variable name
	 */
	@Deprecated
	Object getVariable(Execution execution, String name);

	/**
	 * Returns all variables of process definition
	 */
	@Deprecated
	Set<String> getVariables(Execution execution);

	/**
	 * All variables visible from the given execution scope (including parent scopes).
	 */
	Map<String, Object> getVariables(String executionId);

	/**
	 * All variable values that are defined in the execution scope, without taking outer scopes into account.
	 */
	Map<String, Object> getVariablesLocal(String executionId);

	/**
	 * The variable value. Searching for the variable is done in all scopes that are visible to the given execution
	 * (including parent scopes). Returns null when no variable value is found with the given name or when the value is
	 * set to null.
	 */
	Object getVariable(String executionId, String variableName);

	/**
	 * The variable value. Searching for the variable is done in all scopes that are visible to the given execution
	 * (including parent scopes). Returns null when no variable value is found with the given name or when the value is
	 * set to null. Throws ClassCastException when cannot cast variable to given class
	 */
	<T> T getVariable(String executionId, String variableName, Class<T> variableClass);

	/**
	 * Check whether or not this execution has variable set with the given name, Searching for the variable is done in
	 * all scopes that are visible to the given execution (including parent scopes).
	 */
	boolean hasVariable(String executionId, String variableName);

	/**
	 * The variable value for an execution. Returns the value when the variable is set for the execution (and not
	 * searching parent scopes). Returns null when no variable value is found with the given name or when the value is
	 * set to null.
	 */
	Object getVariableLocal(String executionId, String variableName);

	/**
	 * The variable value for an execution. Returns the value casted to given class when the variable is set for the
	 * execution (and not searching parent scopes). Returns null when no variable value is found with the given name or
	 * when the value is set to null.
	 */
	<T> T getVariableLocal(String executionId, String variableName, Class<T> variableClass);

	/**
	 * Check whether or not this execution has a local variable set with the given name.
	 */
	boolean hasVariableLocal(String executionId, String variableName);

	/**
	 * Update or create a variable for an execution.
	 */
	void setVariable(String executionId, String variableName, Object value);

	/**
	 * Update or create a variable for an execution (not considering parent scopes). If the variable is not already
	 * existing, it will be created in the given execution.
	 */
	void setVariableLocal(String executionId, String variableName, Object value);

	/**
	 * Removes a variable for an execution.
	 */
	void removeVariable(String executionId, String variableName);

	/**
	 * Removes a variable for an execution (not considering parent scopes).
	 */
	void removeVariableLocal(String executionId, String variableName);
}
