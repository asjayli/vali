package com.kitsrc.watt.utils;

import org.apache.commons.lang3.StringUtils;

public class EnvironmentUtil {

    private static volatile String httpProxy;
    private static volatile String httpsProxy;
    private static volatile String noProxy;

    public static String getHttpProxy() {
        if (null == httpProxy) {
            String proxy0 = System.getenv("HTTP_PROXY");
            String proxy1 = System.getenv("http_proxy");
            return (!StringUtils.isEmpty(proxy0) ? proxy0 : proxy1);
        } else {
            return httpProxy;
        }
    }

    public static void setHttpProxy(String httpProxy) {
        EnvironmentUtil.httpProxy = httpProxy;
    }

    public static String getHttpsProxy() {
        if (null == httpsProxy) {
            return System.getenv("HTTPS_PROXY");
        } else {
            return httpsProxy;
        }
    }

    public static void setHttpsProxy(String httpsProxy) {
        EnvironmentUtil.httpsProxy = httpsProxy;
    }


    public static String getNoProxy() {
        if (null == noProxy) {
            return System.getenv("NO_PROXY");
        } else {
            return noProxy;
        }
    }

    public static void setNoProxy(String noProxy) {
        EnvironmentUtil.noProxy = noProxy;
    }

}
