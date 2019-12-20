package com.kitsrc.watt.net;

import com.kitsrc.watt.net.transform.UnmarshallerContext;
import java.util.Map;

public class AcsErrorUnmarshaller {
    public static AcsError unmarshall(AcsError error, UnmarshallerContext context) {
        Map<String, String> map = context.getResponseMap();
        error.setStatusCode(context.getHttpStatus());
        error.setRequestId(map.get("Error.RequestId") == null ? map.get("Error.requestId") : map.get("Error.RequestId"));
        error.setErrorCode(map.get("Error.Code") == null ? map.get("Error.code") : map.get("Error.Code"));
        error.setErrorMessage(map.get("Error.Message") == null ? map.get("Error.message") : map.get("Error.Message"));

        return error;
    }
}
