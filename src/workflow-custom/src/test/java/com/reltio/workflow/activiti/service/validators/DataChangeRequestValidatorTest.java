package com.reltio.workflow.activiti.service.validators;

import com.reltio.workflow.api.tasks.Task;
import com.reltio.workflow.api.validators.RestService;
import com.reltio.workflow.api.validators.ValidationResult;
import com.reltio.workflow.api.validators.ValidationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DataChangeRequestValidatorTest {

    @Test
    void doValidate() throws Exception {
        final DataChangeRequestValidator validator = new DataChangeRequestValidator();
        validator.restService = mock(RestService.class);
        final Task taskWODcrUri = mock(Task.class);
        when(taskWODcrUri.getObjectUris()).thenReturn(List.of("entities/1", "entities/2"));
        final Task validTask = mock(Task.class);
        when(validTask.getObjectUris()).thenReturn(List.of("entities/1", "changeRequests/test"));
        when(validator.restService.entitiesExist("environmentUrl", "tenant", List.of("entities/1"), "accessToken"))
                .thenReturn(Map.of("entities/1", true));
        when(validator.restService.changeRequestsValid("environmentUrl", "tenant", List.of("changeRequests/test"), "accessToken"))
                .thenReturn(Map.of("changeRequests/test", true));
        final Map<Task, ValidationResult> resultMap = validator.doValidate("environmentUrl",
                "tenant",
                "accessToken",
                List.of(taskWODcrUri, validTask));
        Assertions.assertEquals(ValidationStatus.INCORRECT, resultMap.get(taskWODcrUri).getStatus());
        Assertions.assertEquals(ValidationStatus.CORRECT, resultMap.get(validTask).getStatus(), resultMap.get(validTask).getValidationMessage());
    }

    @Test
    void testTaskWithEmptyDCR() throws Exception {
        final DataChangeRequestValidator validator = new DataChangeRequestValidator();
        validator.restService = mock(RestService.class);
        final Task taskWithEmptyDCR = mock(Task.class);
        when(taskWithEmptyDCR.getObjectUris()).thenReturn(List.of("changeRequests/dcr", "entities/entity"));

        when(validator.restService.changeRequestsValid("environmentUrl", "tenant", List.of("changeRequests/dcr"), "accessToken"))
                .thenReturn(Map.of("changeRequests/dcr", false));

        final Map<Task, ValidationResult> resultMap = validator.doValidate("environmentUrl",
                "tenant",
                "accessToken",
                List.of(taskWithEmptyDCR));

        Assertions.assertEquals(ValidationStatus.INCORRECT, resultMap.get(taskWithEmptyDCR).getStatus());
        Assertions.assertEquals("Change request changeRequests/dcr is empty or already applied", resultMap.get(taskWithEmptyDCR).getValidationMessage());
    }
}