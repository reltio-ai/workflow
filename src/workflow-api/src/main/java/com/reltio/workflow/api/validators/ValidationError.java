package com.reltio.workflow.api.validators;

/**
 * Throw this exception in @{@link TaskValidator} if task is not valid
 */
public class ValidationError extends Exception {
    private String message;

    public ValidationError(String message) {
        super(message);
        this.message = message;
    }

    public ValidationError(Throwable cause) {
        super(cause);
    }

    public ValidationError(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

