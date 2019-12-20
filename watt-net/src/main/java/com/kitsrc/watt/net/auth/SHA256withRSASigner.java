

package com.kitsrc.watt.net.auth;

import java.io.UnsupportedEncodingException;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Created by haowei.yao on 2017/9/28.
 */

public class SHA256withRSASigner extends Signer {

    public static final String ENCODING = "UTF-8";
    private static final String ALGORITHM_NAME = "SHA256withRSA";

    @Override
    public String signString(String stringToSign, String accessKeySecret) {
        try {
            Signature rsaSign = Signature.getInstance("SHA256withRSA");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            byte[] keySpec = DatatypeConverter.parseBase64Binary(accessKeySecret);
            PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(keySpec));
            rsaSign.initSign(privateKey);
            rsaSign.update(stringToSign.getBytes(ENCODING));
            byte[] sign = rsaSign.sign();
            String signature = new String(DatatypeConverter.printBase64Binary(sign));
            return signature;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (SignatureException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.toString());
        }
    }

    @Override
    public String signString(String stringToSign, Credentials credentials) {
        return signString(stringToSign, credentials.getAccessKeySecret());
    }

    @Override
    public String getSignerName() {
        return ALGORITHM_NAME;
    }

    @Override
    public String getSignerVersion() {
        return "1.0";
    }

    @Override
    public String getSignerType() {
        return "PRIVATEKEY";
    }
}
