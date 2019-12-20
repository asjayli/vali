package com.kitsrc.watt.net.auth;

//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.utils.AuthUtils;
import com.kitsrc.watt.net.auth.AuthConstant;
import com.kitsrc.watt.net.auth.Credentials;
import com.kitsrc.watt.net.auth.EnvironmentVariableCredentialsProvider;
import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.utils.AuthUtils;
import org.junit.Assert;
import org.junit.Test;

public class EnvironmentVariableCredentialsProviderTest {

    @Test
    public void getCredentialsTest() throws ClientException {
        new AuthConstant();
        EnvironmentVariableCredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        AuthUtils.setClientType("aa");
        Assert.assertNull(provider.getCredentials());

        AuthUtils.setClientType("default");
        AuthUtils.setEnvironmentAccessKeyId("accessKeyIdTest");
        Assert.assertNull(provider.getCredentials());

        AuthUtils.setEnvironmentAccessKeySecret("accessKeyIdTest");
        Credentials credential = provider.getCredentials();
        String accessKeyId = credential.getAccessKeyId();
        String accessKeySecret = credential.getAccessKeySecret();
        Assert.assertEquals("accessKeyIdTest", accessKeyId);
        Assert.assertEquals("accessKeyIdTest", accessKeySecret);

        AuthUtils.setEnvironmentAccessKeyId(null);
        Assert.assertNull(provider.getCredentials());

        AuthUtils.setEnvironmentAccessKeyId("");
        try {
            provider.getCredentials();
            Assert.fail();
        } catch (ClientException e){
            Assert.assertEquals("Environment variable accessKeyId cannot be empty", e.getMessage());
        }
        AuthUtils.setEnvironmentAccessKeyId("a");
        AuthUtils.setEnvironmentAccessKeySecret("");
        try {
            provider.getCredentials();
            Assert.fail();
        } catch (ClientException e){
            Assert.assertEquals("Environment variable accessKeySecret cannot be empty", e.getMessage());
        }

        AuthUtils.setEnvironmentAccessKeyId(null);
        AuthUtils.setEnvironmentAccessKeySecret(null);
    }
}
