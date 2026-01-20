package com.reltio.workflow.activiti.service;

import com.reltio.workflow.api.rest.ReltioConstants;

public class RelationTypeTaskListener extends TypeTaskListener {
    public static final String VARIABLE_NAME = "relationType";

    @Override
    protected String getVariableName() {
        return VARIABLE_NAME;
    }

    @Override
    protected String getPrefix() {
        return ReltioConstants.RELATION_URI_PREFIX;
    }
}
