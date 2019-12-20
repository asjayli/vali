package com.kitsrc.watt.utils.time;

import java.sql.Timestamp;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 高并发场景下System.currentTimeMillis()的性能问题的优化<br>
 * <p>
 * 单线程 后台定时去获取System.currentTimeMillis() 确保每毫秒 仅调用一次该方法<br>
 * <p>
 * System.currentTimeMillis()之所以慢 是因为底层涉及到 用户态与内核态的切换耗时，高并发时容易造成性能瓶颈<br>
 *
 * 转换 native 方法为 类内属性的读取,并发量不影响<br>
 *
 */
public class SystemClock {
    /**
     * 间隔执行延迟时间
     */
    private final long period;
    /**
     * 初始化延时
     */
    private final long initialDelay;
    /**
     * 当前时间戳 数据承载对象
     */
    private final AtomicLong now;

    private SystemClock(long initialDelay, long period) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    private static SystemClock getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SystemClock INSTANCE = new SystemClock(1L, 1L);
    }

    private long get() {
        return now.get();
    }

    /**
     * 返回当前时间戳
     *
     * @return
     */
    public static long now() {
        return getInstance().get();
    }

    /**
     * @return
     */
    public static long currentTimeMillis() {
        return getInstance().get();
    }

    /**
     * @return
     */
    public static String nowDate() {
        return new Timestamp(getInstance().get()).toString();
    }


    private void scheduleClockUpdating() {

        ScheduledExecutorService scheduledExecutorService = newSingleThreadScheduledExecutor();
        //command：执行线程
        //initialDelay：初始化延时
        //period：前一次执行结束到下一次执行开始的间隔时间（间隔执行延迟时间）
        //unit：计时单位
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             now.set(System.currentTimeMillis());
                                                         }
                                                     }, initialDelay, period,
                                                     TimeUnit.MILLISECONDS);
    }

    private ScheduledExecutorService newSingleThreadScheduledExecutor() {

        return new ScheduledThreadPoolExecutor(1,
                                               new SystemClockThreadFactory());

    }


    class SystemClockThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "SystemClockThread");
            thread.setDaemon(true);
            return thread;
        }

    }


}
