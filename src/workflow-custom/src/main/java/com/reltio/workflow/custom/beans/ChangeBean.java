package com.reltio.workflow.custom.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeBean extends TypeBean{
    private JsonNode newValue;
    public JsonNode getNewValue() {
        return newValue;
    }
    public void setNewValue(JsonNode newValue) {
        this.newValue = newValue;
    }
}
