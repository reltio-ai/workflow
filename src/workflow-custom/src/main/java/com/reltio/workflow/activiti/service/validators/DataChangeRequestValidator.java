package com.reltio.workflow.activiti.service.validators;

import com.reltio.workflow.api.WorkflowService;
import com.reltio.workflow.api.rest.ReltioConstants;
import com.reltio.workflow.api.tasks.Task;
import com.reltio.workflow.api.validators.MultipleTasksValidator;
import com.reltio.workflow.api.validators.RestService;
import com.reltio.workflow.api.validators.ValidationResult;
import com.reltio.workflow.api.validators.ValidationStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class DataChangeRequestValidator implements MultipleTasksValidator {
    public static final String VALID_DCR_URI_REGEX = ReltioConstants.CHANGE_REQUEST_URI_PREFIX + "[^/\\s]+";
    @WorkflowService
    RestService restService;

    public Map<Task, ValidationResult> doValidate(String environmentUrl, String tenant, String accessToken, List<Task> tasks) throws Exception {
        Map<Task, ValidationResult> result = new HashMap<>();
        for (Task task : tasks) {
            ValidationResult validationResult = validateObjectUris(task);
            if (validationResult.getStatus() == ValidationStatus.INCORRECT) {
                result.put(task, validationResult);
            }
        }
        //for optimization purpose: verify existence of change request at once
        List<Task> correctTasks = tasks.stream().filter(key -> !result.containsKey(key)).collect(Collectors.toList());
        List<String> changeRequestUris = correctTasks.stream().flatMap(t ->
                        t.getObjectUris().stream().filter(uri -> uri.startsWith(ReltioConstants.CHANGE_REQUEST_URI_PREFIX)))
                .collect(Collectors.toList());
        Map<String, Boolean> changeRequestValidationResult = restService.changeRequestsValid(environmentUrl, tenant, changeRequestUris, accessToken);
        for (Task t : correctTasks) {
            t.getObjectUris().stream().filter(uri -> uri.startsWith(ReltioConstants.CHANGE_REQUEST_URI_PREFIX))
                    .findFirst().ifPresentOrElse(uri -> {
                        Boolean validationResult = changeRequestValidationResult.get(uri);
                        if (validationResult == null) {
                            result.put(t, new ValidationResult(ValidationStatus.INCORRECT, String.format("Change request %s doesn't exist", uri)));
                        } else if (validationResult == Boolean.FALSE) {
                            result.put(t, new ValidationResult(ValidationStatus.INCORRECT, String.format("Change request %s is empty or already applied", uri)));
                        } else {
                            result.put(t, new ValidationResult(ValidationStatus.CORRECT, null));
                        }
                    }, () -> result.put(t, new ValidationResult(ValidationStatus.INCORRECT,
                            String.format("Missed change request object uri: [%s]", t.getObjectUris()))));
        }
        return result;
    }

    private ValidationResult validateObjectUris(Task task) {
        final List<String> objectUris = task.getObjectUris();
        if (objectUris.stream().noneMatch(uri -> uri.startsWith(ReltioConstants.CHANGE_REQUEST_URI_PREFIX))) {
            return new ValidationResult(ValidationStatus.INCORRECT, String.format("Missed change request object uri: [%s]", objectUris));
        }
        final Optional<String> invalidUri = objectUris.stream()
                .filter(uri -> uri.startsWith(ReltioConstants.CHANGE_REQUEST_URI_PREFIX))
                .filter(uri -> !uri.matches(VALID_DCR_URI_REGEX))
                .findAny();
        return invalidUri.map(s -> new ValidationResult(ValidationStatus.INCORRECT, String.format("Wrong change request URI [%s]", s)))
                .orElseGet(() -> new ValidationResult(ValidationStatus.CORRECT, null));
    }
}

