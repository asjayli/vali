package com.kitsrc.watt.net.ft.transform;

//import com.aliyuncs.ft.model.RpcDubboApiResponse;
//import com.aliyuncs.transform.UnmarshallerContext;

import com.kitsrc.watt.net.ft.model.RpcDubboApiResponse;
import com.kitsrc.watt.net.transform.UnmarshallerContext;

public class RpcDubboApiResponseUnmarshaller {

    public static RpcDubboApiResponse unmarshall(RpcDubboApiResponse rpcDubboApiResponse, UnmarshallerContext context) {

        //rpcDubboApiResponse.setRequestId(context.stringValue("GenerateSessionAccessKeyResponse.RequestId"));

        return rpcDubboApiResponse;
    }

}
