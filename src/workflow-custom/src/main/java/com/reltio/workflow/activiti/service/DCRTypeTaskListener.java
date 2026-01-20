package com.reltio.workflow.activiti.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reltio.workflow.api.Utility;
import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.listeners.WorkflowTaskListener;
import com.reltio.workflow.api.rest.ReltioApi;
import com.reltio.workflow.api.rest.ReltioConstants;
import com.reltio.workflow.api.rest.ReltioException;
import com.reltio.workflow.api.tasks.Task;
import com.reltio.workflow.api.tasks.TaskService;
import com.reltio.workflow.custom.beans.ChangeRequest;
import com.reltio.workflow.custom.beans.TypeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DCRTypeTaskListener implements WorkflowTaskListener {
    public static final Logger logger = LoggerFactory.getLogger(DCRTypeTaskListener.class);
    @WorkflowService
    protected TaskService taskService;
    @WorkflowService
    protected ReltioApi reltioApi;
    protected static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private String getVariableName() {
        return "dcrType";
    }

    private String getPrefix() {
        return ReltioConstants.CHANGE_REQUEST_URI_PREFIX;
    }

    @Override
    public void notify(Task task) {
        final List<String> dcrUriList = task.getObjectUris().stream()
                .filter(entityUri -> entityUri.startsWith(getPrefix()))
                .collect(Collectors.toList());
        final List<TypeBean> entityTypes = new ArrayList<>();
        final List<TypeBean> relationTypes = new ArrayList<>();
        final List<TypeBean> changeTypes = new ArrayList<>();
        final List<String> createUris = new ArrayList<>();
        dcrUriList.forEach(uri -> {
            final String url = String.format("%s/%s",
                    Utility.generateTenantUrl(task.getEnvironmentUrl(), task.getTenantId()), uri);
            try {
                final String response = reltioApi.invokeApi(task.getAccessToken(), url, "GET", null);
                final ChangeRequest dcr = objectMapper.readValue(response, ChangeRequest.class);
                if (dcr.getChanges() == null) {
                    logger.warn("Failed to parse changes: {} Response: {}", url, response);
                    return;
                }
                dcr.getChanges().forEach((key, array) -> array.forEach(changeBean -> {
                    changeTypes.add(changeBean);
                    JsonNode jsonNode = changeBean.getNewValue();
                    TypeBean typeBean = null;
                    if (jsonNode != null && jsonNode.isObject() &&
                            jsonNode.has("type") && jsonNode.get("type").isTextual()) {
                        typeBean = new TypeBean();
                        typeBean.setType(jsonNode.get("type").textValue());
                    }
                    switch (changeBean.getType()) {
                        case "CREATE_ENTITY":
                            if (typeBean != null) {
                                entityTypes.add(typeBean);
                            }
                            createUris.add(key);
                            break;
                        case "CREATE_RELATIONSHIP":
                            if (typeBean != null) {
                                relationTypes.add(typeBean);
                            }
                            createUris.add(key);
                            break;
                    }
                }));
            } catch (ReltioException | IOException e) {
                logger.error("Failed to get types: {}", url, e);
            }
        });

        final Stream<TypeBean> entityTypeStream = Stream.concat(entityTypes.stream(), getTypeBeanStream(task,
                ReltioConstants.ENTITY_URI_PREFIX,
                createUris)
        );
        setVariable(task.getId(), EntityTypeTaskListener.VARIABLE_NAME, entityTypeStream);

        final Stream<TypeBean> relationTypeStream = Stream.concat(relationTypes.stream(), getTypeBeanStream(task,
                ReltioConstants.RELATION_URI_PREFIX,
                createUris)
        );
        setVariable(task.getId(), RelationTypeTaskListener.VARIABLE_NAME, relationTypeStream);

        setVariable(task.getId(), getVariableName(), changeTypes.stream());
    }

    private void setVariable(String taskId, String variableName, Stream<TypeBean> typeStream) {
        final String types = typeStream
                .filter(Objects::nonNull)
                .map(TypeBean::getType)
                .filter(Objects::nonNull)
                .sorted()
                .distinct()
                .collect(Collectors.joining(","));
        if (!types.isEmpty()) {
            taskService.setVariable(taskId, variableName, types);
        }
    }

    private Stream<TypeBean> getTypeBeanStream(Task task, String uriPrefix, List<String> uriToIgnore) {
        return task.getObjectUris().stream()
                .filter(uri -> !uriToIgnore.contains(uri))
                .filter(entityUri -> entityUri.startsWith(uriPrefix))
                .map(uri -> {
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
}
