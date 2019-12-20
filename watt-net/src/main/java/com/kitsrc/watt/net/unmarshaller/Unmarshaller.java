package com.kitsrc.watt.net.unmarshaller;

import com.kitsrc.watt.net.AcsResponse;
import com.kitsrc.watt.net.exceptions.ClientException;

public interface Unmarshaller {

    <T extends AcsResponse> T unmarshal(Class<T> clasz, String content) throws ClientException;
}
