package com.kitsrc.watt.utils.json.test;


import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: bailey
 * @Date: 2018/12/14 15:37
 * 发送到MQ的消息体
 */
@Getter
@Setter
@Slf4j
@Accessors(chain = true)
public class MessageTO implements Serializable {

    private static final long serialVersionUID = -8384937918590827650L;
    /**
     * 虚拟主机
     */
    private String virtualHost;
    /**
     * 消息队列名称
     */
    private String queueName;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 服务名称
     */
    @NotBlank
    private String serviceName;
    /**
     * 接口名称
     */
    @NotBlank
    private String actionName;
    /**
     * 数据
     */
    private Object data;

    /**
     * tk
     */
    private String tk;

    /**
     * token
     */
    @NotBlank
    private String token;
    /**
     * Message Delivery QOS
     * Exactly Once 过滤方式实现  每个消息消费正好一次
     */
    private String onceKey;
    /**
     * 消息消费顺序控制 默认为空
     */
    private String seq;
    /**
     * 请求 ID 用于调用链路跟踪
     */
    private String traceId;

}
