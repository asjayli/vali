
package com.kitsrc.watt.net;

import com.kitsrc.watt.net.auth.Credential;
import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.exceptions.ServerException;
import com.kitsrc.watt.net.http.HttpResponse;
import com.kitsrc.watt.net.profile.IClientProfile;

public interface IAcsClient {

           <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request)
            throws ClientException, ServerException;

           <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request,
                                                         boolean autoRetry, int maxRetryCounts)
            throws ClientException, ServerException;

           <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request, IClientProfile profile)
            throws ClientException, ServerException;

           <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request, String regionId,
                                                         Credential credential) throws ClientException, ServerException;

           <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request)
            throws ServerException, ClientException;

           <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request,
                                                    boolean autoRetry, int maxRetryCounts)
            throws ServerException, ClientException;

           <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request,
                                                    IClientProfile profile) throws ServerException, ClientException;

           <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request,
                                                    String regionId, Credential credential)
            throws ServerException, ClientException;

           <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request,
                                                    String regionId)
            throws ServerException, ClientException;

           CommonResponse getCommonResponse(CommonRequest request) throws ServerException, ClientException;

           <T extends AcsResponse> HttpResponse doAction(AcsRequest<T> request, boolean autoRetry,
                                                         int maxRetryCounts, IClientProfile profile)
            throws ClientException, ServerException;

            void restoreSSLCertificate();

            void ignoreSSLCertificate();

            void shutdown();
}