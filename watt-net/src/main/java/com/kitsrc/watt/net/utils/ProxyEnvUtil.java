package com.kitsrc.watt.net.utils;

import com.kitsrc.watt.utils.StringUtil;

public class ProxyEnvUtil {

    private static volatile String httpProxy;
    private static volatile String httpsProxy;
    private static volatile String noProxy;

    public static String getHttpProxy() {
        if (null == httpProxy) {
            String proxy0 = System.getenv("HTTP_PROXY");
            String proxy1 = System.getenv("http_proxy");
            return (!StringUtil.isEmpty(proxy0) ? proxy0 : proxy1);
        } else {
            return httpProxy;
        }
    }

    public static void setHttpProxy(String httpProxy) {
        ProxyEnvUtil.httpProxy = httpProxy;
    }

    public static String getHttpsProxy() {
        if (null == httpsProxy) {
            return System.getenv("HTTPS_PROXY");
        } else {
            return httpsProxy;
        }
    }

    public static void setHttpsProxy(String httpsProxy) {
        ProxyEnvUtil.httpsProxy = httpsProxy;
    }


    public static String getNoProxy() {
        if (null == noProxy) {
            return System.getenv("NO_PROXY");
        } else {
            return noProxy;
        }
    }

    public static void setNoProxy(String noProxy) {
        ProxyEnvUtil.noProxy = noProxy;
    }

}
