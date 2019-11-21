package com.kitsrc.watt.core.guid;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * twitter开源的分布式全局唯一id生成器
 * SnowFlake算法的优点：
 * <p>
 * 1.生成ID时不依赖于DB，完全在内存生成，高性能高可用。基础指标 每秒可生成 1000w条
 * <p>
 * 2.ID呈趋势递增，后续插入索引树的时候性能较好。
 * 多线程建议使用atomic AtomicLong
 * <p>
 * SnowFlake算法的缺点：
 * <p>
 * 依赖于系统时钟的一致性。如果某台机器的系统时钟回拨，有可能造成ID冲突，或者ID乱序。
 *
 * @author : LiJie
 * @date : 2019/3/7
 * @time : 16:48
 * Description:
 */
@Slf4j
public class Snowflake {
    private static final Random RANDOM = new Random();
    /**
     * 初始时间戳 INITIAL TIMESTAMP
     */
    private static final long EPOCH = 1551542400000L;
    /**
     * 机器id所占的位
     */
    private static final long WORKER_ID_BITS = 10L;
    /**
     * 序列号在id中占的位
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 支持的最大机器id，结果是 1023
     * (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = -1L ^ (-1L << Snowflake.WORKER_ID_BITS);

    /**
     * 时间戳的偏移量(12+10) 左移
     */
    private static final long TIMESTAMP_LEFT_OFFSET = Snowflake.SEQUENCE_BITS + Snowflake.WORKER_ID_BITS;
    /**
     * 生成序列的掩码
     */
    private static final long SEQUENCE_MASK = -1L ^ (-1L << Snowflake.SEQUENCE_BITS);
    /**
     * 最大容忍等待时间 即服务器时间回拨后 生成最长的同步等待时长 单位 MS
     */
    private static final long MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS = 11;

    /**
     * 上次生成ID的时间截
     */
    private long lastMilliseconds = -1L;
    /**
     * 工作序列ID(0~1023)
     */
    private final long workerId;
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence;
    /**
     *
     */
    private final long workerIdRightOffset;

    /**
     * workerId 为 0 的构造器 仅允许特殊低TPS场景 勿需分配序列时使用 Deprecated 注解仅做为警告使用
     */
    @Deprecated
    public Snowflake() {
        this.workerId = 0L;
        this.workerIdRightOffset = this.workerId << Snowflake.SEQUENCE_BITS;

        Snowflake.log.debug(
                "worker starting. timestamp left shift {},  worker id bits {}, sequence bits {}, workerid {}",
                Snowflake.TIMESTAMP_LEFT_OFFSET, Snowflake.WORKER_ID_BITS, Snowflake.SEQUENCE_BITS, this.workerId);
    }

    public Snowflake(long workerId) {

        // sanity check for workerId
        // 参数检查
        if (workerId > Snowflake.MAX_WORKER_ID || workerId <= 0) {
            throw new IllegalArgumentException(String.format("\n" +
                    "WorkerID 工作节点ID不能大于 %d 或小于等于0", Snowflake.MAX_WORKER_ID));
        }

        Snowflake.log.debug(
                "worker starting. timestamp left shift {},  worker id bits {}, sequence bits {}, workerid {}",
                Snowflake.TIMESTAMP_LEFT_OFFSET, Snowflake.WORKER_ID_BITS, Snowflake.SEQUENCE_BITS, workerId);

        this.workerId = workerId;
        this.workerIdRightOffset = workerId << Snowflake.SEQUENCE_BITS;
    }

    public Snowflake(long workerId, long lastMilliseconds) {

        // sanity check for workerId
        // 参数检查
        if (workerId > Snowflake.MAX_WORKER_ID || workerId <= 0) {
            throw new IllegalArgumentException(String.format("\n" +
                    "WorkerID 工作节点ID不能大于 %d 或小于等于0", Snowflake.MAX_WORKER_ID));
        }


        Snowflake.log.debug(
                "worker starting. timestamp left shift {},  worker id bits {}, sequence bits {}, workerid {}",
                Snowflake.TIMESTAMP_LEFT_OFFSET, Snowflake.WORKER_ID_BITS, Snowflake.SEQUENCE_BITS, workerId);

        this.workerId = workerId;
        this.workerIdRightOffset = workerId << Snowflake.SEQUENCE_BITS;
        this.lastMilliseconds = lastMilliseconds;
    }

    public long getWorkerId() {
        return this.workerId;
    }


    public synchronized String nextStringId() {
        return String.valueOf(this.nextId());
    }

    /**
     * 执行容忍时间需要的等待
     *
     * @param currentMilliseconds
     * @return
     */
    private boolean waitTolerateTimeDifferenceIfNeed(long currentMilliseconds) throws InterruptedException {
        if (this.lastMilliseconds <= currentMilliseconds) {
            return false;
        }
        long timeDifferenceMilliseconds = this.lastMilliseconds - currentMilliseconds;

        if (timeDifferenceMilliseconds > Snowflake.MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS) {
            throw new IllegalStateException("Clock is moving backwards, last time is " + this.lastMilliseconds + " milliseconds, current time is " + currentMilliseconds + " milliseconds");
        }
        Thread.sleep(timeDifferenceMilliseconds);
        return true;
    }

    /**
     * 获得下一个ID (用同步锁保证线程安全)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < this.lastMilliseconds) {
            Snowflake.log.error("clock is moving backwards.  Rejecting requests until {}.", this.lastMilliseconds);
            throw new RuntimeException(String
                    .format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            this.lastMilliseconds - currentTimestamp));
        }

        if (this.lastMilliseconds == currentTimestamp) {
            this.sequence = (this.sequence + 1) & Snowflake.SEQUENCE_MASK;
            if (this.sequence == 0) {
                //seq 为0的时候表示是下一毫秒时间开始对seq做随机 避免 TPS过低时 生成id 奇偶分布不均
                this.sequence = Snowflake.RANDOM.nextInt(100);
                currentTimestamp = Snowflake.tilNextMillis(this.lastMilliseconds);
            }
        }
        else {
            //如果是新的ms开始
            this.sequence = Snowflake.RANDOM.nextInt(100);
        }

        this.lastMilliseconds = currentTimestamp;
        return ((currentTimestamp - Snowflake.EPOCH) << Snowflake.TIMESTAMP_LEFT_OFFSET) |
                this.workerIdRightOffset |
                this.sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private static long tilNextMillis(long lastTimestamp) {

        while (System.currentTimeMillis() <= lastTimestamp) {
            //System.out.println(n++);
        }
        return System.currentTimeMillis();
        //return System.currentTimeMillis();
    }

}
