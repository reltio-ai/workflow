package com.reltio.workflow.api.rest;

/**
 * Interface of Reltio Cloud Api Error
 */
public interface Errors {
    String getSeverity();

    String getErrorMessage();

    String getErrorDetailMessage();

    Integer getErrorCode();
}
