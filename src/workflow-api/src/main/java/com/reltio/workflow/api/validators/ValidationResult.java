package com.reltio.workflow.api.validators;

/**
 * Throw this validation result class for  @{@link MultipleTasksValidator}
 */
public class ValidationResult {
    private ValidationStatus status;
    private String validationMessage;

    public ValidationResult(ValidationStatus status, String validationMessage) {
        this.status = status;
        this.validationMessage = validationMessage;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public void setStatus(ValidationStatus status) {
        this.status = status;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
}

