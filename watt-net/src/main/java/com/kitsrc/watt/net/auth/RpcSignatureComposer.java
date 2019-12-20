
package com.kitsrc.watt.net.auth;

import com.kitsrc.watt.net.http.FormatType;
import com.kitsrc.watt.net.http.MethodType;
import com.kitsrc.watt.net.utils.ParameterHelper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RpcSignatureComposer implements ISignatureComposer {

    private final static String SEPARATOR = "&";
    private static ISignatureComposer composer = null;

    private RpcSignatureComposer() {

    }

    public static ISignatureComposer getComposer() {
        if (null == composer) {
            composer = new RpcSignatureComposer();
        }
        return composer;
    }

    @Override
    public Map<String, String> refreshSignParameters(Map<String, String> parameters,
                                                     Signer signer, String accessKeyId, FormatType format) {
        Map<String, String> immutableMap = new HashMap<String, String>(parameters);
        immutableMap.put("Timestamp", ParameterHelper.getISO8601Time(new Date()));
        immutableMap.put("SignatureMethod", signer.getSignerName());
        immutableMap.put("SignatureVersion", signer.getSignerVersion());
        immutableMap.put("SignatureNonce", ParameterHelper.getUniqueNonce());
        immutableMap.put("AccessKeyId", accessKeyId);
        if (null != format) {
            immutableMap.put("Format", format.toString());
        }
        if (signer.getSignerType() != null) {
            immutableMap.put("SignatureType", signer.getSignerType());
        }
        return immutableMap;
    }

    @Override
    public String composeStringToSign(MethodType method, String uriPattern,
                                      Signer signer, Map<String, String> queries,
                                      Map<String, String> headers, Map<String, String> paths) {

        String[] sortedKeys = queries.keySet().toArray(new String[]{});
        Arrays.sort(sortedKeys);
        StringBuilder canonicalizedQueryString = new StringBuilder();
        try {
            for (String key : sortedKeys) {
                canonicalizedQueryString.append("&")
                        .append(AcsURLEncoder.percentEncode(key)).append("=")
                        .append(AcsURLEncoder.percentEncode(queries.get(key)));
            }

            StringBuilder stringToSign = new StringBuilder();
            stringToSign.append(method.toString());
            stringToSign.append(SEPARATOR);
            stringToSign.append(AcsURLEncoder.percentEncode("/"));
            stringToSign.append(SEPARATOR);
            stringToSign.append(AcsURLEncoder.percentEncode(
                    canonicalizedQueryString.toString().substring(1)));

            return stringToSign.toString();
        } catch (UnsupportedEncodingException exp) {
            throw new RuntimeException("UTF-8 encoding is not supported.");
        }

    }
}
