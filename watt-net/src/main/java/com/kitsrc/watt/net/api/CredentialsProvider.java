package com.kitsrc.watt.net.api;

import com.kitsrc.watt.net.httpx.exceptions.ClientException;
import com.kitsrc.watt.net.httpx.exceptions.ServerException;

/**
 * Description: </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/12/20  </p>
 * @time : 16:08  </p>
 * Created with IntelliJ IDEA  </p>
 */
public interface CredentialsProvider {
    Credentials getCredentials() throws ClientException, ServerException;
}
