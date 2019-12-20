package com.kitsrc.watt.net;

import com.kitsrc.watt.net.exceptions.ClientException;
import com.kitsrc.watt.net.exceptions.ServerException;
import com.kitsrc.watt.net.transform.UnmarshallerContext;

public abstract class AcsResponse {

    public abstract AcsResponse getInstance(UnmarshallerContext context) throws ClientException, ServerException;

    public boolean checkShowJsonItemName() {
        return true;
    }
}
