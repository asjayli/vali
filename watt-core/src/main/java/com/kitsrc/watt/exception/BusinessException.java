package com.kitsrc.watt.exception;

import com.kitsrc.watt.api.BaseException;
import com.kitsrc.watt.api.ResultStatus;
import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

/**
 * 业务相关异常 追踪部分堆栈
 *
 * @description 业务级别异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException implements BaseException {

    private static String BASE_PACKAGE = "com.vandream";


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

    private BusinessException() {
    }

    public BusinessException(int code, String message) {
        super("code:" + code + ",message:" + message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(int code, String message, Object error) {
        super("code:" + code + ",message:" + message);
        this.code = code;
        this.message = message;
        this.error = error;
    }


    /**
     * @param ResultStatus
     */
    public BusinessException(ResultStatus ResultStatus) {

        super("code:" + ResultStatus.getCode() + ",message:" + ResultStatus.getMessage());
        this.code = ResultStatus.getCode();
        this.message = ResultStatus.getMessage();
    }

    /**
     * @param ResultStatus
     * @param error
     */
    public BusinessException(ResultStatus ResultStatus, Object error) {
        super("code:" + ResultStatus.getCode() + ",message:" + ResultStatus.getMessage());
        this.code = ResultStatus.getCode();
        this.message = ResultStatus.getMessage();
        this.error = error;
    }


    @Override
    public Throwable fillInStackTrace() {
        super.fillInStackTrace();
        StackTraceElement[] stackTrace = this.getStackTrace();
        this.setStackTrace(stackTrace);
        return this;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        List<StackTraceElement> stackTraceElementList = new ArrayList<>();
        StackTraceElement[] stackTraceElementArray = super.getStackTrace();

        for (StackTraceElement stackTraceElement : stackTraceElementArray) {
            if (!stackTraceElement.getClassName()
                    .contains(BASE_PACKAGE)) {
                continue;
            }
            stackTraceElementList.add(stackTraceElement);
        }
        stackTraceElementArray = stackTraceElementList.toArray(new StackTraceElement[stackTraceElementList.size()]);
        return stackTraceElementArray;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


    public BusinessException appendMessage(String appendMsg) {
        this.message = this.message + appendMsg;
        return this;
    }
}
