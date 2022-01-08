package com.vali.constant;

import com.vandream.hamster.core.api.ResultStatus;

/**
 * package name: com.vandream.hamster.common.model
 * project name: hamster-common
 * create time: 2019-10-22 08:57
 *
 * @author tandingbo
 */
public enum ResponseStatus implements ResultStatus {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 系统错误
     */
    ERROR(500, "系统错误");

    private Integer code;
    private String message;

    ResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回状态码 错误码
     *
     * @return
     */
    @Override
    public Integer getCode() {
        return code;
    }

    /**
     * 返回执行消息
     *
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }



}
