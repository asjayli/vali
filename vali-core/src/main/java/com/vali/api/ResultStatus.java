package com.vali.api;

/**
 * 错误信息与错误码的接口 错误状态枚举类 实现
 *
 * @author yu yingxing
 * @date 2018/12/18
 * @time 17:15
 * @description
 */
public interface ResultStatus {
    /**
     * 返回状态码 错误码
     *
     * @return
     */
    Integer getCode();

    /**
     * 返回执行消息
     *
     * @return
     */
    String getMessage();
}
