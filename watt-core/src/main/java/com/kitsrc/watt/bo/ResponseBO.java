package com.kitsrc.watt.bo;

import com.kitsrc.watt.api.ResultStatus;
import com.kitsrc.watt.constant.ResponseStatus;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2018/12/28
 * @time : 15:18
 * Description:
 * 业务层统一返回数据封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBO<T> implements Serializable {
    private static final long serialVersionUID = 6668940043685801570L;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 状态码：200-正常 !=200 错误码
     */
    @NotNull
    private Integer code;

    /**
     * 返回消息内容 包括错误消息
     */
    private String message;
    /**
     * 状态：true 成功 or false 失败
     */
    @NotNull
    private Boolean success;


    public static <T> ResponseBO<T> succeed() {
        return succeedWith(null, ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMessage());
    }

    public static <T> ResponseBO<T> succeed(String message) {
        return succeedWith(null, ResponseStatus.SUCCESS.getCode(), message);
    }

    public static <T> ResponseBO<T> succeed(T data, String message) {
        return succeedWith(data, ResponseStatus.SUCCESS.getCode(), message);
    }

    public static <T> ResponseBO<T> succeed(T data) {
        return succeedWith(data, ResponseStatus.SUCCESS.getCode(), "");
    }

    private static <T> ResponseBO<T> succeedWith(T data, Integer code, String message) {
        return new ResponseBO<>(data, code, message, true);
    }

    public static <T> ResponseBO<T> failed() {
        return failedWith(null, ResponseStatus.ERROR.getCode(), ResponseStatus.ERROR.getMessage());
    }

    public static <T> ResponseBO<T> failed(ResultStatus ResultStatus) {
        return failedWith(null, ResultStatus.getCode(), ResultStatus.getMessage());
    }

    public static <T> ResponseBO<T> failed(String message) {
        return failedWith(null, ResponseStatus.ERROR.getCode(), message);
    }

    public static <T> ResponseBO<T> failed(Integer code, String message) {
        return failedWith(null, code, message);
    }

    public static <T> ResponseBO<T> failed(T data, Integer code, String message) {
        return failedWith(data, code, message);
    }

    private static <T> ResponseBO<T> failedWith(T data, Integer code, String message) {
        return new ResponseBO<>(data, code, message, false);
    }

}
