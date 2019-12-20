

package com.kitsrc.watt.net.auth;

/**
 * Created by haowei.yao on 2017/9/28.
 */

public abstract class Signer {

    private final static Signer HMACSHA1_SIGNER = new HmacSHA1Signer();
    private final static Signer SHA256_WITH_RSA_SIGNER = new SHA256withRSASigner();
    private final static Signer BEARER_TOKEN_SIGNER = new BearerTokenSigner();

    public static Signer getSigner(AlibabaCloudCredentials credentials) {
        if (credentials instanceof KeyPairCredentials) {
            return SHA256_WITH_RSA_SIGNER;
        } else if (credentials instanceof BearerTokenCredentials) {
            return BEARER_TOKEN_SIGNER;
        } else {
            return HMACSHA1_SIGNER;
        }
    }

    public abstract String signString(String stringToSign, AlibabaCloudCredentials credentials);

    public abstract String signString(String stringToSign, String accessKeySecret);

    public abstract String getSignerName();

    public abstract String getSignerVersion();

    public abstract String getSignerType();
}
