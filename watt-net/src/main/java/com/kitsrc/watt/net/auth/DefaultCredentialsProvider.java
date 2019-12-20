package com.kitsrc.watt.net.auth;

import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.utils.AuthUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DefaultCredentialsProvider implements CredentialsProvider {
    private List<CredentialsProvider> defaultProviders = new ArrayList<CredentialsProvider>();
    private static final List<CredentialsProvider> USER_CONFIGURATION_PROVIDERS =
            new Vector<CredentialsProvider>();

    public DefaultCredentialsProvider() throws ClientException {
        defaultProviders.add(new SystemPropertiesCredentialsProvider());
        defaultProviders.add(new EnvironmentVariableCredentialsProvider());
        defaultProviders.add(new ProfileCredentialsProvider());
        String roleName = AuthUtils.getEnvironmentECSMetaData();
        if (roleName != null) {
            if (roleName.length() == 0) {
                throw new ClientException("Environment variable roleName('ALIBABA_CLOUD_ECS_METADATA') cannot be empty");
            }
            defaultProviders.add(new InstanceProfileCredentialsProvider(roleName));
        }
    }

    @Override
    public Credentials getCredentials() throws ClientException {
        Credentials credential;
        if (USER_CONFIGURATION_PROVIDERS.size() > 0) {
            for (CredentialsProvider provider : USER_CONFIGURATION_PROVIDERS) {
                credential = provider.getCredentials();
                if (null != credential) {
                    return credential;
                }
            }
        }
        for (CredentialsProvider provider : defaultProviders) {
            credential = provider.getCredentials();
            if (null != credential) {
                return credential;
            }
        }
        throw new ClientException("not found credentials");
    }

    public static boolean addCredentialsProvider(CredentialsProvider provider) {
        return DefaultCredentialsProvider.USER_CONFIGURATION_PROVIDERS.add(provider);
    }

    public static boolean removeCredentialsProvider(CredentialsProvider provider) {
        return DefaultCredentialsProvider.USER_CONFIGURATION_PROVIDERS.remove(provider);
    }

    public static boolean containsCredentialsProvider(CredentialsProvider provider) {
        return DefaultCredentialsProvider.USER_CONFIGURATION_PROVIDERS.contains(provider);
    }

    public static void clearCredentialsProvider() {
        DefaultCredentialsProvider.USER_CONFIGURATION_PROVIDERS.clear();
    }
}
