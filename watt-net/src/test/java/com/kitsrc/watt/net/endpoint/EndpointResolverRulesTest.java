package com.kitsrc.watt.net.endpoint;

import com.kitsrc.watt.net.endpoint.EndpointResolverRules;
import com.kitsrc.watt.net.endpoint.ResolveEndpointRequest;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

public class EndpointResolverRulesTest {
    @Test
    public void testResolver() {
        EndpointResolverRules endpointResolver = new EndpointResolverRules();
        ResolveEndpointRequest request = new ResolveEndpointRequest("foo", "test", "", "");
        String endpoint = endpointResolver.resolve(request);
        Assert.assertEquals(null, endpoint);
    }

    @Test
    public void testResolverHasEndpoint() {
        EndpointResolverRules endpointResolver = new EndpointResolverRules();
        ResolveEndpointRequest request = new ResolveEndpointRequest("foo", "test", "", "");
        request.setProductEndpointMap(this.getMockEndpointMap());
        request.setProductEndpointRegional("regional");
        String endpoint = endpointResolver.resolve(request);
        Assert.assertEquals("bar", endpoint);
    }

    @Test
    public void testResolverRegional(){
        EndpointResolverRules endpointResolver = new EndpointResolverRules();
        ResolveEndpointRequest request = new ResolveEndpointRequest("cn-hangzhou", "test", "", "");
        request.setProductEndpointMap(this.getMockEndpointMap());
        request.setProductEndpointRegional("regional");
        String endpoint = endpointResolver.resolve(request);
        Assert.assertEquals("test.cn-hangzhou.aliyuncs.com", endpoint);
    }

    @Test
    public void testResolverCentral(){
        EndpointResolverRules endpointResolver = new EndpointResolverRules();
        ResolveEndpointRequest request = new ResolveEndpointRequest("cn-hangzhou", "test", "", "");
        request.setProductEndpointMap(this.getMockEndpointMap());
        request.setProductEndpointRegional("central");
        String endpoint = endpointResolver.resolve(request);
        Assert.assertEquals("test.aliyuncs.com", endpoint);
    }

    @Test
    public void testResolverNetwork(){
        EndpointResolverRules endpointResolver = new EndpointResolverRules();
        ResolveEndpointRequest request = new ResolveEndpointRequest("cn-hangzhou", "test", "", "");
        request.setProductEndpointMap(this.getMockEndpointMap());
        request.setProductEndpointRegional("regional");
        request.setProductNetwork("vpc");
        String endpoint = endpointResolver.resolve(request);
        Assert.assertEquals("test-vpc.cn-hangzhou.aliyuncs.com", endpoint);
    }

    private HashMap<String, String> getMockEndpointMap() {
        HashMap<String, String> endpointMap = new HashMap<String, String>();
        endpointMap.put("foo", "bar");
        return endpointMap;
    }
}
