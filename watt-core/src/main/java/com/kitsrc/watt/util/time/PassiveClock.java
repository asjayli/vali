package com.kitsrc.watt.util.time;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 被动式时钟 </p>
 * 每获取一次 则当前时间步进一个单位
 *
 * @author : LiJie  </p>
 * @date : 2019/10/22  </p>
 * @time : 09:44  </p>
 * Description:  </p>
 */
public class PassiveClock {
    private static final AtomicLong MILLIS = new AtomicLong();

    static {
        MILLIS.set(System.currentTimeMillis());
    }

    public static void init(long value) {
        MILLIS.set(value);
    }

    /**
     * 返回当前毫秒精度的 时间戳  同时时间步进一个毫秒
     *
     * @return
     */
    public static long currentTimeMillis() {
        return MILLIS.getAndAdd(1L);
    }
}
