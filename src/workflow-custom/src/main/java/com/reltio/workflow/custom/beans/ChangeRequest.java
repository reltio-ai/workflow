package com.reltio.workflow.custom.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeRequest {
    @JsonProperty("changes")
    private Map<String, List<ChangeBean>> changes;
    @JsonProperty("changes")
    public Map<String, List<ChangeBean>> getChanges() {
        return changes;
    }
    @JsonProperty("changes")
    public void setChanges(Map<String, List<ChangeBean>> changes) {
        this.changes = changes;
    }
}
