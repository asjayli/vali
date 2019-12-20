package com.kitsrc.watt.net.clients.ahc;

import java.util.Map;
import lombok.ToString;

@ToString(callSuper = true)
public class HttpRequest extends HttpMessage {

    public HttpRequest(String strUrl) {
        super(strUrl);
    }

    public HttpRequest(String strUrl, Map<String, String> tmpHeaders) {
        super(strUrl);
        if (null != tmpHeaders) {
            this.headers = tmpHeaders;
        }
    }
}
