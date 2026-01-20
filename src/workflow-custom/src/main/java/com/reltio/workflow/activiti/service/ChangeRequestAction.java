package com.reltio.workflow.activiti.service;

import com.reltio.workflow.api.actions.Execution;
import com.reltio.workflow.api.rest.ReltioConstants;

public class ChangeRequestAction {
    protected String getChangeRequestUri(Execution execution) {
        String changeRequestUri = null;
        for (String entityUri : execution.getObjectUris()) {
            if (entityUri.startsWith(ReltioConstants.CHANGE_REQUEST_URI_PREFIX)) {
                changeRequestUri = entityUri;
                break;
            }
        }
        return changeRequestUri;
    }
}
