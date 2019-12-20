package com.kitsrc.watt.net.auth;

//import com.aliyuncs.AcsRequest;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.auth.sts.AssumeRoleRequest;
//import com.aliyuncs.auth.sts.AssumeRoleResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.IClientProfile;
import com.kitsrc.watt.net.AcsRequest;
import com.kitsrc.watt.net.IAcsClient;
import com.kitsrc.watt.net.auth.BasicSessionCredentials;
import com.kitsrc.watt.net.auth.Credentials;
import com.kitsrc.watt.net.auth.CredentialsProvider;
import com.kitsrc.watt.net.auth.STSAssumeRoleSessionCredentialsProvider;
import com.kitsrc.watt.net.auth.sts.AssumeRoleRequest;
import com.kitsrc.watt.net.auth.sts.AssumeRoleResponse;
import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.profile.IClientProfile;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class STSAssumeRoleSessionCredentialsProviderTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNormalTest1() {
        Credentials credentials = mock(Credentials.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(credentials,
                                                                                                       roleArn, clientProfile);
        Assert.assertNotNull(provider);
    }

    @Test
    public void policyCredentialTest() throws NoSuchFieldException, IllegalAccessException, ClientException {
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(
                "test", "test", "test", "test", "test",
                "test");
        IAcsClient client = mock(IAcsClient.class);
        AssumeRoleResponse response = mock(AssumeRoleResponse.class);
        AssumeRoleResponse.Credentials credentials = mock(AssumeRoleResponse.Credentials.class);
        when(credentials.getAccessKeyId()).thenReturn("ak");
        when(credentials.getAccessKeySecret()).thenReturn("sk");
        when(credentials.getSecurityToken()).thenReturn("token");
        when(response.getCredentials()).thenReturn(credentials);
        when(client.getAcsResponse(any(AcsRequest.class))).thenReturn(response);
        Class providerClass = provider.getClass();
        Field policy = providerClass.getDeclaredField("policy");
        Field stsClient = providerClass.getDeclaredField("stsClient");
        policy.setAccessible(true);
        stsClient.setAccessible(true);
        stsClient.set(provider, client);
        String policyResult = (String) policy.get(provider);
        Assert.assertEquals("test", policyResult);
        Assert.assertTrue(provider.getCredentials() instanceof BasicSessionCredentials);
    }

    @Test
    public void constructorNormalTest2() {
        CredentialsProvider mockProvider = mock(CredentialsProvider.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(mockProvider,
                roleArn, clientProfile);
        Assert.assertNotNull(provider);
    }

    @Test
    public void constructorNormalTest3() {
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(
                "", "", "", "", "");
        Assert.assertNotNull(provider);
    }

    @Test
    public void constructorWillNullRoleArn() {
        thrown.expect(NullPointerException.class);
        CredentialsProvider mockProvider = mock(CredentialsProvider.class);
        String roleArn = null;
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(mockProvider,
                roleArn, clientProfile);
        Assert.assertNull(provider);
    }

    @Test
    public void getNewRoleSessionNameTest() {
        String roleSessionName = STSAssumeRoleSessionCredentialsProvider.getNewRoleSessionName();
        boolean res = roleSessionName.startsWith("aliyun-java-sdk-");
        Assert.assertTrue(res);
    }

    @Test
    public void withSTSClientTest() {
        IAcsClient acsClient = mock(IAcsClient.class);
        Credentials credentials = mock(Credentials.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(credentials,
                roleArn, clientProfile);
        provider.withSTSClient(acsClient);
        Assert.assertNotNull(provider);
    }

    @Test
    public void withRoleSessionNameTest() {
        Credentials credentials = mock(Credentials.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(credentials,
                roleArn, clientProfile);
        String roleSessionName = "roleSessionName";
        provider.withRoleSessionName(roleSessionName);
        Assert.assertNotNull(provider);
    }

    @Test
    public void withRoleSessionDurationSecondsSmallerThan900Seconds() {
        thrown.expect(IllegalArgumentException.class);
        long duration = 800;
        Credentials credentials = mock(Credentials.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(credentials,
                roleArn, clientProfile);
        provider.withRoleSessionDurationSeconds(duration);

    }

    @Test
    public void withRoleSessionDurationSecondsLargerThan3600Seconds() {
        thrown.expect(IllegalArgumentException.class);
        long duration = 3601;
        Credentials credentials = mock(Credentials.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(credentials,
                roleArn, clientProfile);
        provider.withRoleSessionDurationSeconds(duration);

    }

    @Test
    public void withRoleSessionDurationSecondsNormal() {
        long duration = 1800;
        Credentials credentials = mock(Credentials.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(credentials,
                roleArn, clientProfile);
        provider.withRoleSessionDurationSeconds(duration);
        Assert.assertNotNull(provider);
    }

    @Test
    public void getCredentialsWithNullCredentials() throws ClientException {
        IAcsClient acsClient = mock(IAcsClient.class);
        AssumeRoleResponse roleResponse = mock(AssumeRoleResponse.class);
        AssumeRoleResponse.Credentials credentials = mock(AssumeRoleResponse.Credentials.class);
        when(credentials.getAccessKeyId()).thenReturn("ak");
        when(credentials.getAccessKeySecret()).thenReturn("sk");
        when(credentials.getSecurityToken()).thenReturn("token");
        when(roleResponse.getCredentials()).thenReturn(credentials);
        when(acsClient.getAcsResponse(any(AssumeRoleRequest.class))).thenReturn(roleResponse);
        Credentials credentials1 = mock(Credentials.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(credentials1,
                roleArn, clientProfile);
        provider.withSTSClient(acsClient);
        Credentials credentials2 = provider.getCredentials();
        Assert.assertEquals("ak", credentials2.getAccessKeyId());
    }

    @Test
    public void getCredentialsReturnPreviousCredentials() throws ClientException {
        IAcsClient acsClient = mock(IAcsClient.class);
        AssumeRoleResponse roleResponse = mock(AssumeRoleResponse.class);
        AssumeRoleResponse.Credentials credentials = mock(AssumeRoleResponse.Credentials.class);
        when(credentials.getAccessKeyId()).thenReturn("ak");
        when(credentials.getAccessKeySecret()).thenReturn("sk");
        when(credentials.getSecurityToken()).thenReturn("token");
        when(roleResponse.getCredentials()).thenReturn(credentials);
        when(acsClient.getAcsResponse(any(AssumeRoleRequest.class))).thenReturn(roleResponse);
        Credentials credentials1 = mock(Credentials.class);
        String roleArn = "roleArn";
        IClientProfile clientProfile = mock(IClientProfile.class);
        STSAssumeRoleSessionCredentialsProvider provider = new STSAssumeRoleSessionCredentialsProvider(credentials1,
                roleArn, clientProfile);
        provider.withSTSClient(acsClient);
        Credentials credentials2 = provider.getCredentials();
        Assert.assertEquals("ak", credentials2.getAccessKeyId());

        Credentials credentials3 = provider.getCredentials();
        Assert.assertEquals(credentials2, credentials3);

    }
}
