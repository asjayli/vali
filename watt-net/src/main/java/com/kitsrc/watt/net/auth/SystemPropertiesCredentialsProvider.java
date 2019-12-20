package com.kitsrc.watt.net.auth;

import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.exceptions.ServerException;
import com.kitsrc.watt.net.utils.AuthUtils;
import com.kitsrc.watt.net.utils.StringUtils;


public class SystemPropertiesCredentialsProvider implements AlibabaCloudCredentialsProvider {
    @Override
    public AlibabaCloudCredentials getCredentials() throws ClientException, ServerException {
        if (!"default".equals(AuthUtils.getClientType())) {
            return null;
        }
        String accessKeyId = System.getProperty(AuthConstant.SYSTEM_ACCESSKEYID);
        String accessKeySecret = System.getProperty(AuthConstant.SYSTEM_ACCESSKEYSECRET);
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret)) {
            return null;
        }
        return new BasicCredentials(accessKeyId, accessKeySecret);
    }
}
