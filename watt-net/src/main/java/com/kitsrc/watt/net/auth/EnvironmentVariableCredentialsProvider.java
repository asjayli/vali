package com.kitsrc.watt.net.auth;

import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.utils.AuthUtils;

public class EnvironmentVariableCredentialsProvider implements AlibabaCloudCredentialsProvider {
    @Override
    public AlibabaCloudCredentials getCredentials() throws ClientException {
        if (!"default".equals(AuthUtils.getClientType())) {
            return null;
        }

        String accessKeyId = AuthUtils.getEnvironmentAccessKeyId();
        String accessKeySecret = AuthUtils.getEnvironmentAccessKeySecret();
        if (accessKeyId == null || accessKeySecret == null) {
            return null;
        }
        if (accessKeyId.length() == 0) {
            throw new ClientException("Environment variable accessKeyId cannot be empty");
        }
        if (accessKeySecret.length() == 0) {
            throw new ClientException("Environment variable accessKeySecret cannot be empty");
        }
        return new BasicCredentials(accessKeyId, accessKeySecret);
    }
}
