package com.reltio.workflow.api.actions;

import java.util.List;

/**
 * Class for getting data about execution of process definition
 */
public interface Execution {
	/**
	 * Returns access token of current user
	 */
    String getAccessToken();

	/**
	 * Returns environment url of Reltio Cloud
	 */
    String getEnvironmentUrl();

	/**
	 * Returns tenant Id of Reltio Cloud
	 */
    String getTenantId();

	/**
	 * Returns list of object uris of current process instance
	 */
    List<String> getObjectUris();

	/**
	 * Returns process definition Id
	 */
    String getProcessDefinitionId();

	/**
	 * Returns process instance id
	 */
    String getProcessInstanceId();

	/**
	 * Returns the id of the parent of this execution. If null, the execution represents a process-instance.
	 */
	String getParentId();

	/**
	 * The unique identifier of the execution.
	 */
	String getId();

}
