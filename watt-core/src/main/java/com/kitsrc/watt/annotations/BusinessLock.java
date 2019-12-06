package com.kitsrc.watt.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 同一业务接口 同一时间 同一单据 只有一个任务执行
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface BusinessLock {
    /**
     * 业务主键id
     *
     * @return
     */
    String businessKey();


    /**
     * 锁超时时间
     *
     * @return
     */
    int timeout() default 3000;

    /**
     * 是否允许异步调用
     */
    boolean async() default true;

}
