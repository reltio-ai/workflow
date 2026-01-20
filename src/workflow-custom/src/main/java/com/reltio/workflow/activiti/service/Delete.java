package com.reltio.workflow.activiti.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.actions.Execution;
import com.reltio.workflow.api.actions.WorkflowAction;
import com.reltio.workflow.api.rest.ReltioApi;
import com.reltio.workflow.api.rest.ReltioConstants;
import com.reltio.workflow.api.rest.ReltioException;
import com.reltio.workflow.api.rest.Response;
import com.reltio.workflow.api.rest.beans.ReltioResponse;

/**
 * Delete entity from Reltio Cloud
 */
public class Delete implements WorkflowAction {
    public static final Logger logger = LoggerFactory.getLogger(Delete.class);
	// Injectected service
    @WorkflowService
    private ReltioApi reltioService;

    public void execute(Execution execution) throws Exception {
        logger.info("In Delete class");
        String accessToken = execution.getAccessToken();
        String environmentUrl = execution.getEnvironmentUrl();
        String tenantId = execution.getTenantId();
        List<String> objectURIs = execution.getObjectUris();

		String entityURI = objectURIs.get(0);
		String url = environmentUrl + "/reltio/api/" + tenantId + "/" + entityURI;
        String responseJson = reltioService.invokeApi(accessToken, url, ReltioConstants.DELETE, "");
        ObjectMapper objectMapper = new ObjectMapper();
        Response reltioResponse = objectMapper.readValue(responseJson, ReltioResponse.class);
        if (reltioResponse.getStatus() == null || !reltioResponse.getStatus().equals(ReltioConstants.SUCCESS)) {
            throw new ReltioException(responseJson);
        }
    }
}
