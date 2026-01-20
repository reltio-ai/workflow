package com.reltio.workflow.api.services;

import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.Credentials;

public interface AwsService {
    Credentials getTemporaryCredentials(AssumeRoleRequest assumeRoleRequest);
}
