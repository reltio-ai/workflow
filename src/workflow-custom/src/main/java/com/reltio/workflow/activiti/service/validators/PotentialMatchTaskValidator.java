/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reltio.workflow.activiti.service.validators;

import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.tasks.Task;
import com.reltio.workflow.api.validators.MultipleTasksValidator;
import com.reltio.workflow.api.validators.RestService;
import com.reltio.workflow.api.validators.ValidationResult;
import com.reltio.workflow.api.validators.ValidationStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PotentialMatchTaskValidator implements MultipleTasksValidator {
    @WorkflowService
    RestService restService;

    public Map<Task, ValidationResult> doValidate(String environmentUrl, String tenant, String accessToken, List<Task> tasks) throws Exception {
        Map<Task, ValidationResult> result = new HashMap<>();
        for (Task task : tasks) {
            List<String> objectUris = task.getObjectUris();
            if (objectUris.size() != 2) {
                result.put(task, new ValidationResult(ValidationStatus.INCORRECT, "Task should have 2 uris only"));
            }
        }

        List<Task> correctTasks = tasks.stream().filter(key -> !result.containsKey(key)).collect(Collectors.toList());
        List<String> allEntities = correctTasks.stream().flatMap(t -> t.getObjectUris().stream()).collect(Collectors.toList());
        Map<String, Boolean> entityValidationResult = restService.entitiesExist(environmentUrl, tenant,
                allEntities, accessToken);
        for (Task t : correctTasks) {
            String firstEntity = t.getObjectUris().get(0);
            Boolean validationResult = entityValidationResult.get(firstEntity);

            if (validationResult == null) {
                result.put(t, new ValidationResult(ValidationStatus.INCORRECT, String.format("Entity %s does not exist", firstEntity)));
            } else if (validationResult == Boolean.FALSE) {
                result.put(t, new ValidationResult(ValidationStatus.INCORRECT, String.format("Entity %s does not exist", firstEntity)));
            } else {
                String secondEntity = t.getObjectUris().get(1);
                Boolean secondValidationResult = entityValidationResult.get(secondEntity);
                if (secondValidationResult == null) {
                    result.put(t, new ValidationResult(ValidationStatus.INCORRECT, String.format("Entity %s does not exist", secondEntity)));
                } else if (secondValidationResult == Boolean.FALSE) {
                    result.put(t, new ValidationResult(ValidationStatus.INCORRECT, String.format("Entity %s does not exist", secondEntity)));
                } else {
                    List<String> matches = restService.getMatches(t.getEnvironmentUrl(), t.getTenantId(), firstEntity, accessToken);

                    if (!matches.contains(secondEntity)) {
                        result.put(t, new ValidationResult(ValidationStatus.INCORRECT, String.format("Entity %s doesn't match %s", firstEntity, secondEntity)));
                    } else {
                        result.put(t, new ValidationResult(ValidationStatus.CORRECT, null));
                    }
                }

            }
        }
        return result;

    }
}
