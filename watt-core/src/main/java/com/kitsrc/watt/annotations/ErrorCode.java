package com.kitsrc.watt.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 错误码状态区段预占用标记
 *
 * @author : LiJie
 * @date : 2019/4/18 0018
 * @time : 11:08
 * Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface ErrorCode {
    /**
     * 开始编号
     *
     * @return
     */
    int begin();

    /**
     * 结束编号
     *
     * @return
     */
    int end();
}
