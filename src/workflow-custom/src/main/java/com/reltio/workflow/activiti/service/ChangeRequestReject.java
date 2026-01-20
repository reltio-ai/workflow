package com.reltio.workflow.activiti.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reject data change request
 */
public class ChangeRequestReject extends ChangeRequestAction implements WorkflowAction {
    public static final Logger logger = LoggerFactory.getLogger(ChangeRequestReject.class);
    // Injectected service
    @WorkflowService
    private ReltioApi reltioApi;

    public void execute(Execution execution) throws Exception {
        logger.info("In ChangeRequestReject class");
        String accessToken = execution.getAccessToken();
        String environmentUrl = execution.getEnvironmentUrl();
        String tenantId = execution.getTenantId();
        String changeRequestUri = getChangeRequestUri(execution);

        //Reltio Call
        String url = Utility.generateTenantUrl(environmentUrl, tenantId) + "/" + changeRequestUri + "/_reject";
        String responseJson = reltioApi.invokeApi(accessToken, url, ReltioConstants.POST, "");
        ObjectMapper objectMapper = new ObjectMapper();
        Response reltioResponse = objectMapper.readValue(responseJson, ReltioResponse.class);
        if (reltioResponse.getStatus() == null || !reltioResponse.getStatus().equals(ReltioConstants.SUCCESS)) {
            throw new ReltioException(responseJson);
        }
    }

}
