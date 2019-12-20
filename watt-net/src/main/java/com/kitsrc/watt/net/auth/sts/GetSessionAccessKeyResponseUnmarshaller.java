package com.kitsrc.watt.net.auth.sts;

import com.kitsrc.watt.net.auth.sts.GenerateSessionAccessKeyResponse.SessionAccessKey;
import com.kitsrc.watt.net.transform.UnmarshallerContext;

public class GetSessionAccessKeyResponseUnmarshaller {

    public static GenerateSessionAccessKeyResponse unmarshall(GenerateSessionAccessKeyResponse getSessionAccessKeyResponse,
                                                              UnmarshallerContext context) {

        getSessionAccessKeyResponse.setRequestId(context.stringValue("GenerateSessionAccessKeyResponse.RequestId"));

        SessionAccessKey credentials = new SessionAccessKey();
        credentials.setSessionAccessKeyId(context.stringValue("GenerateSessionAccessKeyResponse.SessionAccessKey.SessionAccessKeyId"));
        credentials.setSessionAccessKeySecert(context.stringValue("GenerateSessionAccessKeyResponse.SessionAccessKey.SessionAccessKeySecret"));
        credentials.setExpiration(context.stringValue("GenerateSessionAccessKeyResponse.SessionAccessKey.Expiration"));

        getSessionAccessKeyResponse.setSessionAccessKey(credentials);

        return getSessionAccessKeyResponse;
    }
}
