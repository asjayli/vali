package com.kitsrc.watt.net.auth;

import com.kitsrc.watt.net.DefaultAcsClient;
import com.kitsrc.watt.net.IAcsClient;
import com.kitsrc.watt.net.auth.sts.AssumeRoleRequest;
import com.kitsrc.watt.net.auth.sts.AssumeRoleResponse;
import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.exceptions.ServerException;
import com.kitsrc.watt.net.profile.DefaultProfile;
import com.kitsrc.watt.net.profile.IClientProfile;

/**
 * This implementation of CredentialsProvider accesses Alibaba Cloud STS service to assume a Role and get
 * back a temporary session for authentication.
 */
public class STSAssumeRoleSessionCredentialsProvider implements CredentialsProvider {

    /**
     * Default duration for started sessions.
     */
    public static final int DEFAULT_DURATION_SECONDS = 3600;
    /**
     * The arn of the role to be assumed.
     */
    private final String roleArn;
    /**
     * For test
     * To know how many rounds AssumeRole has been called
     */
    public long assumeRoleRound = 0;
    /**
     * The client for starting STS sessions.
     */
    private IAcsClient stsClient;
    /**
     * An identifier for the assumed role session.
     */
    private String roleSessionName;
    /**
     * The Duration for assume role sessions.
     */
    private long roleSessionDurationSeconds;

    private String policy;
    private BasicSessionCredentials credentials = null;


    public STSAssumeRoleSessionCredentialsProvider(Credentials longLivedCredentials,
                                                   String roleArn, IClientProfile clientProfile) {
        this(new StaticCredentialsProvider(longLivedCredentials), roleArn, clientProfile);
    }

    public STSAssumeRoleSessionCredentialsProvider(CredentialsProvider longLivedCredentialsProvider,
                                                   String roleArn, IClientProfile clientProfile) {
        if (roleArn == null) {
            throw new NullPointerException(
                    "You must specify a value for roleArn.");
        }
        this.roleArn = roleArn;
        this.roleSessionName = getNewRoleSessionName();
        this.stsClient = new DefaultAcsClient(clientProfile, longLivedCredentialsProvider);
        this.roleSessionDurationSeconds = DEFAULT_DURATION_SECONDS;
    }

    public STSAssumeRoleSessionCredentialsProvider(String accessKeyId, String accessKeySecret, String roleSessionName,
                                                   String roleArn, String regionId) {
        this.roleArn = roleArn;
        this.roleSessionName = roleSessionName;
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        this.stsClient = new DefaultAcsClient(profile);
        this.roleSessionDurationSeconds = AuthConstant.TSC_VALID_TIME_SECONDS;
    }

    public STSAssumeRoleSessionCredentialsProvider(String accessKeyId, String accessKeySecret, String roleSessionName,
                                                   String roleArn, String regionId, String policy) {
        this(accessKeyId, accessKeySecret, roleSessionName, roleArn, regionId);
        this.policy = policy;
    }

    public static String getNewRoleSessionName() {
        return "aliyun-java-sdk-" + System.currentTimeMillis();
    }

    public STSAssumeRoleSessionCredentialsProvider withRoleSessionName(String roleSessionName) {
        this.roleSessionName = roleSessionName;
        return this;
    }

    public STSAssumeRoleSessionCredentialsProvider withRoleSessionDurationSeconds(long roleSessionDurationSeconds) {
        if (roleSessionDurationSeconds < 900 || roleSessionDurationSeconds > 3600) {
            throw new IllegalArgumentException(
                    "Assume Role session duration should be in the range of 15min - 1Hr");
        }
        this.roleSessionDurationSeconds = roleSessionDurationSeconds;
        return this;
    }

    public STSAssumeRoleSessionCredentialsProvider withSTSClient(IAcsClient client) {
        this.stsClient = client;
        return this;
    }

    @Override
    public Credentials getCredentials() throws ClientException, ServerException {
        if (credentials == null || credentials.willSoonExpire()) {
            credentials = getNewSessionCredentials();
        }
        return credentials;
    }

    private BasicSessionCredentials getNewSessionCredentials() throws ClientException, ServerException {

        assumeRoleRound += 1;

        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest();
        assumeRoleRequest.setRoleArn(roleArn);
        assumeRoleRequest.setRoleSessionName(roleSessionName);
        assumeRoleRequest.setDurationSeconds(roleSessionDurationSeconds);
        if (null != policy){
            assumeRoleRequest.setPolicy(policy);
        }
        AssumeRoleResponse response = stsClient.getAcsResponse(assumeRoleRequest);
        return new BasicSessionCredentials(
                response.getCredentials().getAccessKeyId(),
                response.getCredentials().getAccessKeySecret(),
                response.getCredentials().getSecurityToken(),
                roleSessionDurationSeconds
        );
    }
}