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


public class SingleEntityTaskValidator implements MultipleTasksValidator {
    @WorkflowService
    RestService restService;
    public Map<Task, ValidationResult> doValidate(String environmentUrl, String tenant, String accessToken, List<Task> tasks) throws Exception {
        Map<Task, ValidationResult> result = new HashMap<>();
        List<String> allEntities = tasks.stream().flatMap(t -> t.getObjectUris().stream()).collect(Collectors.toList());
        Map<String, Boolean> entityValidationResult = restService.entitiesExist(environmentUrl, tenant,
                allEntities, accessToken);
        for (Task task : tasks) {
            ValidationResult validationResult = validateObjectUris(task, entityValidationResult);
            result.put(task, validationResult);
        }
        return result;
    }

    private ValidationResult validateObjectUris(Task task, Map<String, Boolean> entityValidationResult) throws Exception {
        List<String> objectUris = task.getObjectUris();
        if (objectUris.size() == 0) {
            return new ValidationResult(ValidationStatus.INCORRECT, "There is not object uris");
        }
        if (objectUris.size() > 1) {
            return new ValidationResult(ValidationStatus.INCORRECT, "Task has more than one entity");
        } else {
            String entityUri = task.getObjectUris().get(0);
            Boolean secondValidationResult = entityValidationResult.get(entityUri);
            if (secondValidationResult == null) {
                return new ValidationResult(ValidationStatus.INCORRECT, "Entity " + entityUri + " does not exist");
            } else if (secondValidationResult == Boolean.FALSE) {
                return new ValidationResult(ValidationStatus.INCORRECT, "Entity " + entityUri + " does not exist");
            }
        }
        return new ValidationResult(ValidationStatus.CORRECT, null);
    }

}
