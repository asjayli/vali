package com.kitsrc.watt.net.endpoint;

import com.kitsrc.watt.net.exceptions.ClientException;

public interface EndpointResolver {
    String resolve(ResolveEndpointRequest request) throws ClientException;
}
