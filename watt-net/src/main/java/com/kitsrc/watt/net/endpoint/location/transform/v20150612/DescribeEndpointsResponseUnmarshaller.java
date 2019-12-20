

package com.kitsrc.watt.net.endpoint.location.transform.v20150612;

import com.kitsrc.watt.net.endpoint.location.model.v20150612.DescribeEndpointsResponse;
import com.kitsrc.watt.net.endpoint.location.model.v20150612.DescribeEndpointsResponse.Endpoint;
import com.kitsrc.watt.net.transform.UnmarshallerContext;
import java.util.ArrayList;
import java.util.List;

public class DescribeEndpointsResponseUnmarshaller {

    public static DescribeEndpointsResponse unmarshall(DescribeEndpointsResponse describeEndpointsResponse,
                                                       UnmarshallerContext context) {

        describeEndpointsResponse.setRequestId(context.stringValue("DescribeEndpointsResponse.RequestId"));
        describeEndpointsResponse.setSuccess(context.booleanValue("DescribeEndpointsResponse.Success"));

        List<Endpoint> endpoints = new ArrayList<Endpoint>();
        for (int i = 0; i < context.lengthValue("DescribeEndpointsResponse.Endpoints.Length"); i++) {
            Endpoint endpoint = new Endpoint();
            endpoint.setEndpoint(context.stringValue("DescribeEndpointsResponse.Endpoints[" + i + "].Endpoint"));
            endpoint.setId(context.stringValue("DescribeEndpointsResponse.Endpoints[" + i + "].Id"));
            endpoint.setNamespace(context.stringValue("DescribeEndpointsResponse.Endpoints[" + i + "].Namespace"));
            endpoint.setSerivceCode(context.stringValue("DescribeEndpointsResponse.Endpoints[" + i + "].SerivceCode"));
            endpoint.setType(context.stringValue("DescribeEndpointsResponse.Endpoints[" + i + "].Type"));

            List<String> protocols = new ArrayList<String>();
            for (int j = 0; j < context
                    .lengthValue("DescribeEndpointsResponse.Endpoints[" + i + "].Protocols.Length"); j++) {
                protocols.add(
                        context.stringValue("DescribeEndpointsResponse.Endpoints[" + i + "].Protocols[" + j + "]"));
            }
            endpoint.setProtocols(protocols);

            endpoints.add(endpoint);
        }
        describeEndpointsResponse.setEndpoints(endpoints);

        return describeEndpointsResponse;
    }
}
