package com.kitsrc.watt.net.auth;

import com.kitsrc.watt.net.profile.IClientProfile;

public class StaticCredentialsProvider implements CredentialsProvider {

    private Credentials credentials = null;

    public StaticCredentialsProvider(Credentials credentials) {
        this.credentials = credentials;
    }

    @SuppressWarnings("deprecation")
    public StaticCredentialsProvider(IClientProfile clientProfile) {
        IClientProfile clientProfile1 = clientProfile;
        Credential legacyCredential = clientProfile1.getCredential();
        if (null != legacyCredential.getSecurityToken()) {
            this.credentials = new BasicSessionCredentials(legacyCredential.getAccessKeyId(), legacyCredential
                    .getAccessSecret(), legacyCredential.getSecurityToken());
        } else {
            this.credentials = new LegacyCredentials(legacyCredential);
        }
    }

    @Override
    public Credentials getCredentials() {
        return this.credentials;
    }
}
