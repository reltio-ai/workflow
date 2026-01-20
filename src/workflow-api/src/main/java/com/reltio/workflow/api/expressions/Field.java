package com.reltio.workflow.api.expressions;

import com.reltio.workflow.api.actions.Execution;
import com.reltio.workflow.api.tasks.Task;

/**
 * Field interface allows injecting fields from BPMN schema
 */
public interface Field {

    /**
     * Get value of the field for the given execution
     *
     * @param execution execution
     * @return value of the field
     */
    Object getValue(Execution execution);

    /**
     * Get value of the field for the given task
     *
     * @param task execution
     * @return value of the field
     */

    Object getValue(Task task);

    /**
     * Get expression text
     *
     * @return expression text
     */
    String getExpressionText();
}
