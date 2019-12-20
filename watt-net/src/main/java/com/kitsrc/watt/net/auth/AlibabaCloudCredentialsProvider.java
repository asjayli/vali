package com.kitsrc.watt.net.auth;

import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.exceptions.ServerException;

public interface AlibabaCloudCredentialsProvider {

     AlibabaCloudCredentials getCredentials() throws ClientException, ServerException;
}
