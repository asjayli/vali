package com.vali.api;


/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/4/17 0017
 * @time : 8:43
 * Description:
 */
public interface BaseException {
    /**
     * 异常错误码
     *
     * @return
     */
    int getCode();

    /**
     * 异常 message
     *
     * @return
     */
    String getMessage();

    /**
     * 错误详情数据
     *
     * @return
     */

    Object getError();
}
