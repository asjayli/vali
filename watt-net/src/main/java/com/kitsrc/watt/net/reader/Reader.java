package com.kitsrc.watt.net.reader;

import com.kitsrc.watt.net.exceptions.ClientException;
import java.util.Map;

@Deprecated
public interface Reader {

           Map<String, String> read(String response, String endpoint) throws ClientException;

           Map<String, String> readForHideArrayItem(String response, String endpoint) throws ClientException;
}
