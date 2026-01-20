package com.reltio.workflow.api;


import com.reltio.workflow.api.rest.ReltioConstants;

/**
 * Construct Reltio Cloud api url
 */
public class Utility {
    public static String generateTenantUrl(String environmentUrl, String tenantId) {
        return environmentUrl + ReltioConstants.RELTIO_REST_API_URL + tenantId;
    }

}
