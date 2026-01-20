/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reltio.workflow.api.rest.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.reltio.workflow.api.rest.Errors;
import com.reltio.workflow.api.rest.Response;

/**
 * Class of Reltio Cloud Api response, All responses from api should be deserialize to this class
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReltioResponse implements Response {

    private Integer index;
    private Boolean successful;
    private Object object;
    private ReltioErrors errors;
    private String error;
    private String error_description;
    private Integer errorCode;
    private String severity;
    private String errorMessage;
    private String errorDetailMessage;
    private Object innerErrorData;
    private String uri;
    private String status;
    private String state;
    private Integer responseCode;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the successful
     */
    public Boolean getSuccessful() {
        return successful;
    }

    /**
     * @param successful the successful to set
     */
    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    /**
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    @JsonIgnore
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @return the errors
     */
    public ReltioErrors getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(ReltioErrors errors) {
        this.errors = errors;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the error_description
     */
    public String getError_description() {
        return error_description;
    }

    /**
     * @param error_description the error_description to set
     */
    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    /**
     * @return the error_code
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the error_code to set
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * @param severity the severity to set
     */
    @JsonIgnore
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * @return the innerErrorData
     */
    public Object getInnerErrorData() {
        return innerErrorData;
    }

    /**
     * @param innerErrorData the innerErrorData to set
     */
    public void setInnerErrorData(Object innerErrorData) {
        this.innerErrorData = innerErrorData;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorDetailMessage
     */
    public String getErrorDetailMessage() {
        return errorDetailMessage;
    }

    /**
     * @param errorDetailMessage the errorDetailMessage to set
     */
    public void setErrorDetailMessage(String errorDetailMessage) {
        this.errorDetailMessage = errorDetailMessage;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public class ReltioErrors implements Errors {

        private String severity;
        private String errorMessage;
        private Integer errorCode;
        private String errorDetailMessage;

        /**
         * @return the severity
         */
        public String getSeverity() {
            return severity;
        }

        /**
         * @param severity the severity to set
         */
        public void setSeverity(String severity) {
            this.severity = severity;
        }

        /**
         * @return the errorMessage
         */
        public String getErrorMessage() {
            return errorMessage;
        }

        /**
         * @param errorMessage the errorMessage to set
         */
        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        /**
         * @return the errorDetailMessage
         */
        public String getErrorDetailMessage() {
            return errorDetailMessage;
        }

        /**
         * @param errorDetailMessage the errorDetailMessage to set
         */
        public void setErrorDetailMessage(String errorDetailMessage) {
            this.errorDetailMessage = errorDetailMessage;
        }

        /**
         * @return the errorCode
         */
        public Integer getErrorCode() {
            return errorCode;
        }

        /**
         * @param errorCode the errorCode to set
         */
        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

    }
}
