package com.kitsrc.watt.net.api;

import com.kitsrc.watt.net.http.exceptions.ClientException;
import com.kitsrc.watt.net.http.exceptions.ServerException;

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
