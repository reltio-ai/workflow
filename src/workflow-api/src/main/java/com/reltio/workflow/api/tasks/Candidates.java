package com.reltio.workflow.api.tasks;

import java.util.List;
/**
 * Class that contains information about task's candidates
 */
public class Candidates {
    private final List<String> candidateUsers;
    private final List<String> candidateGroups;

    public Candidates(List<String> candidateUsers, List<String> candidateGroups) {
        this.candidateUsers = candidateUsers;
        this.candidateGroups = candidateGroups;
    }

	/**
	 * Candidate users, can be null
	 */
    public List<String> getCandidateUsers() {
        return candidateUsers;
    }

	/**
	 * Candidate groups, can be null
	 */
    public List<String> getCandidateGroups() {
        return candidateGroups;
    }
}