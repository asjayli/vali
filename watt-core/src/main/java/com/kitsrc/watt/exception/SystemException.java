package com.kitsrc.watt.exception;


import com.kitsrc.watt.api.BaseException;
import com.kitsrc.watt.api.ResultStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统相关异常 追踪全部堆栈
 *
 * @description 系统级别异常
 */
@Getter
@Setter
public class SystemException extends RuntimeException implements BaseException {

    private static final long serialVersionUID = -7200104305557723751L;
    /**
     * 错误编码
     */
    public int code;
    /**
     * 错误 提示语 提示文案
     */
    public String message;
    /**
     * 错误详情
     */
    public Object error;

    private SystemException() {

    }

    public SystemException(int code, String message) {
        super("code:" + code + ",message:" + message);
        this.code = code;
        this.message = message;
    }

    public SystemException(int code, String message, Object error) {
        super("code:" + code + ",message:" + message);
        this.code = code;
        this.message = message;
        this.error = error;
    }


    public SystemException(ResultStatus ResultStatus) {
        super("code:" + ResultStatus.getCode() + ",message:" + ResultStatus.getMessage());
        this.code = ResultStatus.getCode();
        this.message = ResultStatus.getMessage();
    }

    public SystemException(ResultStatus ResultStatus, Object error) {
        super("code:" + ResultStatus.getCode() + ",message:" + ResultStatus.getMessage());
        this.code = ResultStatus.getCode();
        this.message = ResultStatus.getMessage();
        this.error = error;
    }


    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    public SystemException appendMessage(String appendMsg) {
        this.message = this.message + appendMsg;
        return this;
    }
}
