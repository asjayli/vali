package com.kitsrc.watt.net;

import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.exceptions.ServerException;
import com.kitsrc.watt.net.http.HttpResponse;
import com.kitsrc.watt.net.transform.UnmarshallerContext;

public class CommonResponse extends AcsResponse {

    private String data;

    private int httpStatus;

    private HttpResponse httpResponse;

    @Override
    public AcsResponse getInstance(UnmarshallerContext context) throws ClientException, ServerException {
        return null;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
}
