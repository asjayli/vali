package com.kitsrc.watt.net.auth;

public class CredentialsProviderFactory {
    public <T extends AlibabaCloudCredentialsProvider> T createCredentialsProvider(T classInstance) {
        return classInstance;
    }
}
