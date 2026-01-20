package com.reltio.workflow.api.validators;

import java.util.List;
import java.util.Map;

public interface RestService {
    boolean entityExists(String environmentUrl, String tenant, String entityUri, String accessToken) throws Exception;

    boolean changeRequestValid(String environmentUrl, String tenant, String changeRequestUri, String accessToken) throws Exception;

    Map<String, Boolean> entitiesExist(String environmentUrl, String tenant, List<String> entityUris, String accessToken) throws Exception;

    Map<String, Boolean> changeRequestsValid(String environmentUrl, String tenant, List<String> changeRequestUris, String accessToken) throws Exception;

    List<String> getMatches(String environmentUrl, String tenantId, String objectUri, String accessToken) throws Exception;
}
