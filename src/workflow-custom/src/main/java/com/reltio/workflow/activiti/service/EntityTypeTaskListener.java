package com.reltio.workflow.activiti.service;

import com.reltio.workflow.api.rest.ReltioConstants;

public class EntityTypeTaskListener extends TypeTaskListener {
    public static final String VARIABLE_NAME = "entityType";

    @Override
    protected String getVariableName() {
        return VARIABLE_NAME;
    }

    @Override
    protected String getPrefix() {
        return ReltioConstants.ENTITY_URI_PREFIX;
    }
}
