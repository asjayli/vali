package com.kitsrc.watt.net.auth;

public class CredentialsProviderFactory {
    public <T extends CredentialsProvider> T createCredentialsProvider(T classInstance) {
        return classInstance;
    }
}
