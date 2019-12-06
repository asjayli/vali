package com.kitsrc.watt.constant;


import java.io.Serializable;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/7
 * @time : 13:11
 * Description:
 */
@Getter
public class RequestHeader implements Serializable {


    /**
     * APP key  客户端识别码 只处理合法识别码的请求
     * 全局
     */
    public static final String APP_KEY = "X-Ca-App-Key";
    /**
     * Encryption Key 加密秘钥
     * 全局
     */
    public static final String SECRET_KEY = "X-Ca-Secret-Key";
    /**
     * 鉴权令牌
     * 登录
     */
    public static final String SECRET_TOKEN = "X-Ca-Secret-Token";

    /**
     * API 版本号
     * 全局
     */
    public static final String API_VERSION = "X-Ca-Version";
    /**
     * 请求签名 请求数据摘要 防篡改验证
     * 全局
     */
    public static final String SECRET_SIGNATURE = "X-Ca-Signature";
    /**
     * 唯一请求id  Number once 一个只被使用一次的任意或非重复的随机(数)值
     * 全局
     */
    public static final String SECRET_NONCE = "X-Ca-Nonce";

}
