package com.kitsrc.watt.net.auth;

//import com.aliyuncs.profile.IClientProfile;
import com.kitsrc.watt.net.auth.*;
import com.kitsrc.watt.net.profile.IClientProfile;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class StaticCredentialsProviderTest {

    @Test
    public void constructorNormal() {
        Credentials credentials = mock(Credentials.class);
        StaticCredentialsProvider provider = new StaticCredentialsProvider(credentials);
        Assert.assertEquals(credentials, provider.getCredentials());
    }

    @Test
    public void testConstructorLegacyCredentials() {
        Credential credential = mock(Credential.class);
        Mockito.when(credential.getSecurityToken()).thenReturn(null);
        IClientProfile profile = mock(IClientProfile.class);
        Mockito.when(profile.getCredential()).thenReturn(credential);

        StaticCredentialsProvider provider = new StaticCredentialsProvider(profile);
        Assert.assertTrue(provider.getCredentials() instanceof LegacyCredentials);

    }

    @Test
    public void testConstructorBasicSessionCredentials() {
        Credential credential = mock(Credential.class);
        Mockito.when(credential.getSecurityToken()).thenReturn("securityToken");
        Mockito.when(credential.getAccessKeyId()).thenReturn("accessKeyId");
        Mockito.when(credential.getAccessSecret()).thenReturn("accessSecret");
        IClientProfile profile = mock(IClientProfile.class);
        Mockito.when(profile.getCredential()).thenReturn(credential);

        StaticCredentialsProvider provider = new StaticCredentialsProvider(profile);
        Assert.assertTrue(provider.getCredentials() instanceof BasicSessionCredentials);
    }

}
