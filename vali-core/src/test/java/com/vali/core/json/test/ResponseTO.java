package com.vali.core.json.test;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/23
 * @time : 17:18
 * Description:
 */
@Getter
@Setter
@Slf4j
@Accessors(chain = true)
public class ResponseTO implements Serializable {
    private static final long serialVersionUID = -642854885871978590L;
    /**
     * 状态：true 成功 or false 失败
     */
    @NotNull
    private Boolean success;

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
     * 返回数据结果
     */
    private Object data;
    /**
     * 错误详情
     */
    private Object error;
    /**
     * 请求 ID 用于调用链路跟踪
     */
    private String traceId;
    /**
     * 返回的 data 数据的 SHA-256 校验签名
     */
    private String sha256;


    public ResponseTO() {
        // 悲观开局
        this.success = false;
        this.code = 500;
    }


    public ResponseTO appendMessage(String appendMsg) {
        this.message = this.message + appendMsg;
        return this;
    }

}
