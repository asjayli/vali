
package com.kitsrc.watt.net.exceptions;

public class ServerException extends ClientException {

    private static final long serialVersionUID = -7345371390798165336L;

    public ServerException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        this.setErrorType(ErrorType.Server);
    }

    public ServerException(String errCode, String errMsg, String requestId) {
        this(errCode, errMsg);
        this.setRequestId(requestId);
    }
}
