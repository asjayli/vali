package com.kitsrc.watt.core.guid;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 被动式时间 </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/10/22  </p>
 * @time : 09:44  </p>
 * Description:  </p>
 */
public class PassiveTime {
    private static final AtomicLong MILLIS = new AtomicLong();

    static {
        MILLIS.set(System.currentTimeMillis());
    }

    public static void init(long value) {
        MILLIS.set(value);
    }

    public static long currentTimeMillis() {
        return MILLIS.getAndAdd(1L);
    }
}
