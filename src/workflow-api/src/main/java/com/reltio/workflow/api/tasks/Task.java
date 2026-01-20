package com.reltio.workflow.api.tasks;

import java.util.Date;
import java.util.List;

/**
 * Class for getting data about a task
 */
public interface Task {
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
	 * Returns list of object uris of current task
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
	 * Returns execution id
	 */
	String getExecutionId();

	/**
	 * Returns assignee of current task
	 */
    String getAssignee();

	/**
	 * Returns id of current task
	 */
    String getId();

	/**
	 * Returns name of current task
	 */
    String getName();

	/**
	 * Returns owner of current task
	 */
    String getOwner();

	/**
	 * Returns creator of task
	 */
    String getReltioUser();

	/**
	 * Returns due data of task
	 */
    Date getDueDate();

	/**
	 * Returns task definition key of task
	 */
    String getTaskDefinitionKey();
}
