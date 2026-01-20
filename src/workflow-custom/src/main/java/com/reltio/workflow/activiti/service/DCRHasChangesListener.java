package com.reltio.workflow.activiti.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reltio.workflow.api.Utility;
import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.actions.Execution;
import com.reltio.workflow.api.actions.ExecutionService;
import com.reltio.workflow.api.listeners.WorkflowExecutionListener;
import com.reltio.workflow.api.rest.ReltioApi;
import com.reltio.workflow.api.rest.ReltioConstants;
import com.reltio.workflow.api.rest.ReltioException;
import com.reltio.workflow.custom.beans.ChangeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DCRHasChangesListener implements WorkflowExecutionListener {
    public static final Logger logger = LoggerFactory.getLogger(DCRHasChangesListener.class);
    public static final String VARIABLE_NAME = "hasChanges";
    @WorkflowService
    protected ReltioApi reltioApi;
    @WorkflowService
    protected ExecutionService executionService;
    protected static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public void notify(Execution execution) {
        final List<String> dcrUriList = execution.getObjectUris().stream()
                .filter(entityUri -> entityUri.startsWith(ReltioConstants.CHANGE_REQUEST_URI_PREFIX))
                .collect(Collectors.toList());
        for (String uri : dcrUriList) {
            final String url = String.format("%s/%s",
                    Utility.generateTenantUrl(execution.getEnvironmentUrl(), execution.getTenantId()), uri);
            try {
                final String response = reltioApi.invokeApi(execution.getAccessToken(), url, "GET", null);
                final ChangeRequest dcr = objectMapper.readValue(response, ChangeRequest.class);
                if (dcr.getChanges() == null || dcr.getChanges().isEmpty()) {
                    logger.error("Empty DCR: [{}] Response: {}", uri, response);
                    executionService.setVariable(execution.getId(), VARIABLE_NAME, "false");
                    return;
                }
            } catch (IOException | ReltioException e) {
                logger.error("Fail to read DCR: [{}]", uri, e);
                executionService.setVariable(execution.getId(), VARIABLE_NAME, "false");
                return;
            }
        }
        executionService.setVariable(execution.getId(),VARIABLE_NAME, "true");
    }
}
