package com.kitsrc.watt.net.auth.sts;

import com.kitsrc.watt.net.auth.sts.AssumeRoleResponse.AssumedRoleUser;
import com.kitsrc.watt.net.auth.sts.AssumeRoleResponse.Credentials;
import com.kitsrc.watt.net.transform.UnmarshallerContext;


public class AssumeRoleResponseUnmarshaller {

    public static AssumeRoleResponse unmarshall(AssumeRoleResponse assumeRoleResponse, UnmarshallerContext context) {

        assumeRoleResponse.setRequestId(context.stringValue("AssumeRoleResponse.RequestId"));

        Credentials credentials = new Credentials();
        credentials.setSecurityToken(context.stringValue("AssumeRoleResponse.Credentials.SecurityToken"));
        credentials.setAccessKeySecret(context.stringValue("AssumeRoleResponse.Credentials.AccessKeySecret"));
        credentials.setAccessKeyId(context.stringValue("AssumeRoleResponse.Credentials.AccessKeyId"));
        credentials.setExpiration(context.stringValue("AssumeRoleResponse.Credentials.Expiration"));
        assumeRoleResponse.setCredentials(credentials);

        AssumedRoleUser assumedRoleUser = new AssumedRoleUser();
        assumedRoleUser.setArn(context.stringValue("AssumeRoleResponse.AssumedRoleUser.Arn"));
        assumedRoleUser.setAssumedRoleId(context.stringValue("AssumeRoleResponse.AssumedRoleUser.AssumedRoleId"));
        assumeRoleResponse.setAssumedRoleUser(assumedRoleUser);

        return assumeRoleResponse;
    }
}