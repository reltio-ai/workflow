package com.reltio.workflow.api.processes;

import java.util.List;
import java.util.Map;

/**
 * Service to get data/ update for process instance
 */
public interface ProcessesService {
	/*
		Add comment for process instance
	 */
	void addComment(String processId, String message);

	/*
		Get comments for process instance
	 */
	List<Comment> getComments(String processId);
}
