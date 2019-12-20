package com.kitsrc.watt.net.auth;

import com.kitsrc.watt.net.auth.*;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class SignerTest {
    @Test
    public void getSignerReturnSha256WithRSASigner() {
        Credentials credentials = mock(KeyPairCredentials.class);
        Signer signer = Signer.getSigner(credentials);
        Assert.assertEquals(true, SHA256withRSASigner.class.isAssignableFrom(signer.getClass()));
    }

    @Test
    public void getSignerReturnBearerTokenSigner() {
        Credentials credentials = mock(BearerTokenCredentials.class);
        Signer signer = Signer.getSigner(credentials);
        Assert.assertEquals(true, BearerTokenSigner.class.isAssignableFrom(signer.getClass()));
    }

    @Test
    public void getSignerReturnHmacSHA1Signer() {
        Credentials credentials = mock(Credentials.class);
        Signer signer = Signer.getSigner(credentials);
        Assert.assertEquals(true, HmacSHA1Signer.class.isAssignableFrom(signer.getClass()));
    }

}
