

package com.kitsrc.watt.net.endpoint;

import com.kitsrc.watt.net.DefaultAcsClient;

public class InternalLocationServiceEndpointResolver extends LocationServiceEndpointResolver {

    private final static String INNER_LOCATION_SERVICE_ENDPOINT = "location-inner.aliyuncs.com";
    private final static String INNER_LOCATION_SERVICE_API_VERSION = "2015-12-25";

    public InternalLocationServiceEndpointResolver(DefaultAcsClient client) {
        super(client);
        this.locationServiceEndpoint = INNER_LOCATION_SERVICE_ENDPOINT;
        this.locationServiceApiVersion = INNER_LOCATION_SERVICE_API_VERSION;
    }
}
