package com.kitsrc.watt.util.security;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.vandream.hamster.core.exception.SystemException;
import com.vandream.hamster.core.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author LiJie
 * @Date 2019-02-11 17:17
 * 消息加解密所用
 */
public class AESUtil {
    private static Logger log = LoggerFactory.getLogger(AESUtil.class);
    /**
     * BEGIN:静态代码块执行 与书写顺序严格相关 此段代码位置不可更改
     */

    private static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    static {
        Security.addProvider(BOUNCY_CASTLE_PROVIDER);
    }

    /**
     * 算法/模式/补码方式
     */
    private static final String AES_ALGORITHM = "AES/ECB/PKCS7Padding";
    /**
     * 静态缓存 Cipher 实例 安全性已测试验证 性能提升 节省约 2000ms/次
     */
    private static Cipher cipherDecryptInstance = initializeCipherDecryptInstance();
    private static Cipher cipherEncryptInstance = initializeCipherEncryptInstance();
    /**
     * END:静态代码块执行 与书写顺序严格相关 此段代码位置不可更改
     */
    private static final Base64 BASE64 = new Base64();

    /**
     * 初始化解密用 Cipher 实例
     *
     * @return
     */
    private static synchronized Cipher initializeCipherDecryptInstance() {
        try {
            return Cipher.getInstance(AES_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化加密用 Cipher 实例
     *
     * @return
     */
    private static synchronized Cipher initializeCipherEncryptInstance() {
        try {
            return Cipher.getInstance(AES_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey, int keyLength) throws SystemException {
        try {
            // 判空
            if (StringUtil.isBlank(sKey)) {
                log.error("AES {}", "Key为空null");
                return null;
            }
            // 判断Key长度
            if (sKey.length() != keyLength) {
                log.error("AES {}", "Key长度不是32位");
                return null;
            }

            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES_ALGORITHM);
            // 判空校验加密 cipher 实例
            if (cipherEncryptInstance == null) {
                cipherEncryptInstance = initializeCipherDecryptInstance();
            }
            byte[] encrypted;
            synchronized (cipherEncryptInstance) {
                cipherEncryptInstance.init(Cipher.ENCRYPT_MODE, skeySpec);

                encrypted = cipherEncryptInstance.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
            }
            //此处使用BASE64做转码功能，同时能起到2次加密的作用。
            String cipherText = BASE64.encodeToString(encrypted);
            if (StringUtil.isBlank(cipherText)) {
                throw new RuntimeException("AES 加密异常");
            }
            return cipherText;
            //}

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 解密
     *
     * @param sSrc
     * @param sKey
     * @return
     */
    public static String decrypt(String sSrc, String sKey, int keyLength) throws SystemException {

        try {
            log.debug("decrypt -> sSrc:{}\n", sSrc);
            log.debug("decrypt -> sKey:{}\n", sKey);
            log.debug("decrypt -> keyLength:{}\n", keyLength);
            // 判断Key是否为空
            if (StringUtil.isBlank(sKey)) {
                log.error("AES {}", "Key为空null");

                throw new RuntimeException("AES Key为空null");
            }
            //判断Key长度
            if (sKey.length() != keyLength) {
                log.error("AES {}", "Key长度不是32位");

                throw new RuntimeException("Key长度不是32位");
                //return null;
            }
            byte[] keyBytes = sKey.getBytes(StandardCharsets.UTF_8);
            log.debug("decrypt -> sKey.byte:{}\n", new String(keyBytes, StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);

            //先用base64解密
            byte[] encrypted = BASE64.decode(sSrc);
            // 判空校验解密 cipher 实例
            if (cipherDecryptInstance == null) {
                cipherDecryptInstance = initializeCipherDecryptInstance();
            }
            byte[] original;
            // 线程安全
            synchronized (cipherDecryptInstance) {
                // 初始化 Cipher 实例
                cipherDecryptInstance.init(Cipher.DECRYPT_MODE, skeySpec);
                // 执行解密

                original = cipherDecryptInstance.doFinal(encrypted);
            }
            String originalString = new String(original, StandardCharsets.UTF_8);
            log.debug("解密后原文：{}\n", originalString);
            return originalString;
            //}


        }
        catch (Exception e) {
            log.error("AES 解密异常 {},{}", e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }

}