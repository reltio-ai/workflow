package com.reltio.workflow.api.rest;

/**
 * Interface of Reltio Cloud Api response
 */
public interface Response {
    Integer getResponseCode();

    Integer getIndex();

    Boolean getSuccessful();

    Object getObject();

    Errors getErrors();

    String getError();

    String getError_description();

    Integer getErrorCode();

    String getSeverity();

    Object getInnerErrorData();

    String getErrorMessage();

    String getErrorDetailMessage();

    String getStatus();

}
