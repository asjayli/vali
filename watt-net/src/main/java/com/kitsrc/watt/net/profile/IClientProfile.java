package com.kitsrc.watt.net.profile;


import com.kitsrc.watt.net.auth.CredentialsProvider;
import com.kitsrc.watt.net.auth.Credential;
import com.kitsrc.watt.net.auth.ISigner;
import com.kitsrc.watt.net.http.FormatType;
import com.kitsrc.watt.net.http.HttpClientConfig;
import org.slf4j.Logger;

@SuppressWarnings("deprecation")
public interface IClientProfile {



           String getRegionId();

           FormatType getFormat();

    /**
     * Deprecated : Use CredentialsProvider getCredentials() instead of this
     */
    /*@Deprecated*/
    Credential getCredential();

    /**
     * This method exists because ClientProfile holds too much modules like endpoint management
     *
     * @param credentialsProvider
     */
        void setCredentialsProvider(CredentialsProvider credentialsProvider);





    /**
     * http client configs
     */
          HttpClientConfig getHttpClientConfig();

          void setHttpClientConfig(HttpClientConfig httpClientConfig);

          void enableUsingInternalLocationService();

          boolean isUsingInternalLocationService();

          boolean isUsingVpcEndpoint();

          void enableUsingVpcEndpoint();



          Logger getLogger();

          void setLogger(Logger logger);

          String getLogFormat();

          void setLogFormat(String logFormat);
}
