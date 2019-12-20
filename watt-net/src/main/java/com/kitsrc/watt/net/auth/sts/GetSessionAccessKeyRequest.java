
package com.kitsrc.watt.net.auth.sts;

import com.kitsrc.watt.net.RpcAcsRequest;

public class GetSessionAccessKeyRequest extends RpcAcsRequest<GenerateSessionAccessKeyResponse> {

    private static String version = "2015-04-01";

    private static String product = "Sts";

    private static String action = "GenerateSessionAccessKey";
    private int durationSeconds = 3600;
    private String publicKeyId;

    public GetSessionAccessKeyRequest() {
        super(product, version, action);
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
        putQueryParameter("DurationSeconds", durationSeconds);
    }

    public String getPublicKeyId() {
        return publicKeyId;
    }

    public void setPublicKeyId(String publicKeyId) {
        this.publicKeyId = publicKeyId;
        putQueryParameter("PublicKeyId", publicKeyId);
    }

    @Override
    public Class<GenerateSessionAccessKeyResponse> getResponseClass() {
        return GenerateSessionAccessKeyResponse.class;
    }
}
