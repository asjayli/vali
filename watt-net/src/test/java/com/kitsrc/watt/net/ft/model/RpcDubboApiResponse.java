package com.kitsrc.watt.net.ft.model;

//import com.aliyuncs.AcsResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.exceptions.ServerException;
//import com.aliyuncs.transform.UnmarshallerContext;

import com.kitsrc.watt.net.AcsResponse;
import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.exceptions.ServerException;
import com.kitsrc.watt.net.transform.UnmarshallerContext;

public class RpcDubboApiResponse extends AcsResponse {

    @Override
    public AcsResponse getInstance(UnmarshallerContext context) throws ClientException, ServerException {
        return null;
    }

}
