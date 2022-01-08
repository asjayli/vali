package com.vali.constant;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2018/12/17
 * @time : 16:43
 * Description:
 */
public enum RequestMethod {
    /**
     * Used when the client is requesting a resource on the Web server.<p/>
     * 求获取Request-URI所标识的资源
     */
    GET,

    /**
     * Used when the client is sending information or data to the server—for example, filling out an online form (i.e. Sends a
     * large amount of complex data to the Web Server).<p/>
     *
     * 当客户端向服务端发送信息或者数据的时候使用--例如，表单提交（向Web服务器发送大量的复杂的数据）。
     */
    POST,

    /**
     * Used when the client is requesting some information about a resource but not requesting the resource itself.<p/>
     *
     * 当客户端向Web服务器请求一个资源的一些信息而不是资源的全部信息的时候使用。
     */
    HEAD,

    /**
     * Used when the client is sending a replacement document or uploading a new document to the Web server under the request URL.
     *
     * 　　　　当客户端向Web服务端指定URL发送一个替换的文档或者上传一个新文档的时候使用。
     */
    PUT,

    /**
     * Used when the client is trying to delete a document from the Web server, identified by the request URL.
     *
     *  当客户端尝试从Web服务端删除一个由请求URL唯一标识的文档的时候使用。
     */
    DELETE,

    /**
     * Used when the client is asking the available proxies or intermediate servers changing the request to announce themselves.
     *
     * 　　　　当客户端要求可用的代理服务器或者中间服务更改请求来宣布自己的时候使用。
     * 请求服务器回送收到的请求信息，主要用于测试或诊断
     */
    TRACE,

    /**
     * 用于将局部修改应用到资源。
     * 向指定资源位置上传其最新内容（部分更新，非幂等）
     */
    PATCH,

    /**
     * Used when the client wants to determine other available methods to retrieve or process a document on the Web server.
     *
     * 当客户端想要决定其他可用的方法来检索或者处理Web服务端的一个文档时使用。
     * 返回服务器针对特定资源所支持的HTTP请求方法。 也可以利用向Web服务器发送'*'的请求来测试服务器的功能性
     */
    OPTIONS,

    ;

}
