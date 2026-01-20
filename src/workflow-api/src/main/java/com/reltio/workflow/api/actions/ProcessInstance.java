package com.reltio.workflow.api.actions;

import java.util.List;

/**
 * Class for getting data about execution of process definition
 */
public interface ProcessInstance {
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
     * The unique identifier of the process instance.
     */
    String getId();

}
