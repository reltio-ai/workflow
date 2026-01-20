package com.reltio.workflow.activiti.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reltio.workflow.api.Utility;
import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.listeners.WorkflowTaskListener;
import com.reltio.workflow.api.rest.ReltioApi;
import com.reltio.workflow.api.rest.ReltioException;
import com.reltio.workflow.api.tasks.Task;
import com.reltio.workflow.api.tasks.TaskService;
import com.reltio.workflow.custom.beans.TypeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract public class TypeTaskListener implements WorkflowTaskListener {
    public static final Logger logger = LoggerFactory.getLogger(TypeTaskListener.class);
    @WorkflowService
    protected TaskService taskService;
    @WorkflowService
    protected ReltioApi reltioApi;
    protected static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public void notify(Task task) {
        final List<String> objectURIs = task.getObjectUris().stream()
                .filter(entityUri -> entityUri.startsWith(getPrefix()))
                .collect(Collectors.toList());
        final String types = getTypeBeanStream(task, objectURIs)
                .filter(Objects::nonNull)
                .map(TypeBean::getType)
                .filter(Objects::nonNull)
                .sorted()
                .distinct()
                .collect(Collectors.joining(","));
        if(!types.isEmpty()) {
            taskService.setVariable(task.getId(), getVariableName(), types);
        }
    }

    protected Stream<TypeBean> getTypeBeanStream(Task task, List<String> objectURIs) {
        return objectURIs.stream().map(uri -> {
            final String url = String.format("%s/%s",
                    Utility.generateTenantUrl(task.getEnvironmentUrl(), task.getTenantId()), uri);
            try {
                final String response = reltioApi.invokeApi(task.getAccessToken(), url, "GET", null);
                return objectMapper.readValue(response, TypeBean.class);
            } catch (ReltioException | IOException e) {
                logger.error("Failed to get types: {}", url, e);
            }
            return null;
        });
    }

    protected abstract String getVariableName();

    protected abstract String getPrefix();
}
