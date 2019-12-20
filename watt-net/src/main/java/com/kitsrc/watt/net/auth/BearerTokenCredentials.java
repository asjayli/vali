package com.kitsrc.watt.net.auth;

public class BearerTokenCredentials implements Credentials {

    private String bearerToken;

    public BearerTokenCredentials(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    @Override
    public String getAccessKeyId() {
        return null;
    }

    @Override
    public String getAccessKeySecret() {
        return null;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

}
