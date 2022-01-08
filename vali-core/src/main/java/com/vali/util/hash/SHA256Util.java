package com.vali.util.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.vandream.hamster.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/4/1
 * @time : 13:50
 * Description:
 */
@Slf4j
public class SHA256Util {

    private static final String SHA_ALGORITHM = "SHA-256";
    private static MessageDigest MESSAGE_DIGEST;

    static {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance(SHA_ALGORITHM);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public static String encode(String src) {
        byte[] srcBytes = src.getBytes();
        if (ObjectUtil.isEmpty(MESSAGE_DIGEST)) {
            try {
                MESSAGE_DIGEST = MessageDigest.getInstance(SHA_ALGORITHM);
            }
            catch (NoSuchAlgorithmException e) {
                log.error(e.getMessage(), e);
            }
        }
        MESSAGE_DIGEST.reset();
        MESSAGE_DIGEST.update(srcBytes);
        byte[] destBytes = MESSAGE_DIGEST.digest();
        return bytes2String(destBytes);
    }

    private static String bytes2String(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return sb.toString();
    }

}
