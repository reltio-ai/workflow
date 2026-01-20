package com.reltio.workflow.activiti.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reltio.workflow.api.Utility;
import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.actions.Execution;
import com.reltio.workflow.api.actions.WorkflowAction;
import com.reltio.workflow.api.rest.ReltioApi;
import com.reltio.workflow.api.rest.ReltioConstants;
import com.reltio.workflow.api.rest.ReltioException;
import com.reltio.workflow.api.rest.Response;
import com.reltio.workflow.api.rest.beans.ReltioResponse;

/**
 * Remove potential match for specific entities from Reltio Cloud
 */
public class NotAMatch implements WorkflowAction {
    public static final Logger logger = LoggerFactory.getLogger(NotAMatch.class);
	// Injectected service
    @WorkflowService
    private ReltioApi reltioService;

    public void execute(Execution execution) throws Exception {
        logger.info("In Not a Match class");

        String accessToken = execution.getAccessToken();
        String environmentUrl = execution.getEnvironmentUrl();
        String tenantId = execution.getTenantId();

        List<String> objectURIs = execution.getObjectUris();
        String entityURI1 = objectURIs.get(0);
        String entityURI2 = objectURIs.get(1);

        //Reltio Call
        String url = Utility.generateTenantUrl(environmentUrl, tenantId) + "/" + entityURI1 + "/_notMatch?uri=" + entityURI2;
        String responseJson = reltioService.invokeApi(accessToken, url, ReltioConstants.POST, "");
        ObjectMapper objectMapper = new ObjectMapper();
        Response reltioResponse = objectMapper.readValue(responseJson, ReltioResponse.class);
        if (reltioResponse.getStatus() == null) {
            throw new ReltioException(responseJson);
        }
    }
}
