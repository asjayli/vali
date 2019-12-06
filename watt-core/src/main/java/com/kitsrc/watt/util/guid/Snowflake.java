package com.kitsrc.watt.util.guid;

import java.util.concurrent.ThreadLocalRandom;

import com.vandream.hamster.core.util.time.SystemClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.ResourceUtils;

/**
 * twitter开源的分布式全局唯一id生成器
 * SnowFlake算法的优点：
 * <p>
 * 1.生成ID时不依赖于DB，完全在内存生成，高性能高可用。
 * <p>
 * 2.ID呈趋势递增，后续插入索引树的时候性能较好。
 * <p>
 * SnowFlake算法的缺点：
 * <p>
 * 依赖于系统时钟的一致性。如果某台机器的系统时钟回拨，有可能造成ID冲突，或者ID乱序。
 * <p> 1024*4096=4,194,304  每毫秒可提供的最大唯一ID 数量 419W</p>
 * <p>预留 0 号段，不对外分发，供程序内部使用</p>
 * <p> 最终可用量 961*4095=3,935,295 每毫秒 393W</p>
 * <p> 每实例 每毫秒 每机器ID 最大可提供 4096 个</p>
 *
 * @author : LiJie
 * @date : 2019/3/7
 * @time : 16:48
 * Description:
 */
public final class Snowflake {
    private final Logger log = LoggerFactory.getLogger(Snowflake.class);
    /**
     * 初始时间戳
     */
    private static final long EPOCH_TIME_STAMP = 1551542400000L;
    /**
     * 机器id所占的位
     */
    private static final long WORKER_ID_BITS = 5L;
    /**
     * 数据标识id所占的位数
     */
    private static final long DATACENTER_ID_BITS = 5L;
    /**
     * 序列号在id中占的位
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 支持的最大机器id，结果是31   0~31
     * (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    /**
     * 支持的最大数据标识id，结果是31  0~31
     */
    private static final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);
    /**
     * 机器ID的偏移量(12) 左移
     */
    private static final long WORKERID_OFFSET = SEQUENCE_BITS;
    /**
     * 数据中心ID的偏移量(12+5) 左移
     */
    private static final long DATACENTERID_OFFSET = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 时间戳的偏移量(5+5+12) 左移
     */
    private static final long TIMESTAMP_OFFSET = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    /**
     * 生成序列的掩码
     */
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);
    /**
     * 上次生成ID的时间截
     */
    private long lastMilliseconds = -1L;
    /**
     * 工作节点ID(0~31)
     */
    private long workerId;
    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence;

    public Snowflake() {

    }

    public Snowflake(long datacenterId, long workerId, long sequence) {
        ResourceUtils.
        // sanity check for workerId
        // 参数检查
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("\n" +
                    "WorkerID 工作节点ID不能大于 %d 或小于0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("DataCenterID 数据标识 Id 不能大于 %d 或小于 0", MAX_DATACENTER_ID));
        }
        log.debug(
                "worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid {}",
                TIMESTAMP_OFFSET, DATACENTER_ID_BITS, WORKER_ID_BITS, SEQUENCE_BITS, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    //---------------测试---------------
    public static void main(String[] args) {
        Snowflake worker = new Snowflake(1, 1, 3);
        for (int i = 0; i < 30; i++) {
            System.out.println(worker.nextId());
        }
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获得下一个ID (用同步锁保证线程安全)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastMilliseconds) {
            log.error("clock is moving backwards.  Rejecting requests until {}.", lastMilliseconds);
            throw new RuntimeException(String
                    .format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            lastMilliseconds - currentTimestamp));
        }


        if (lastMilliseconds == currentTimestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 同一毫秒的序列数已经达到最大
                //seq 为0的时候表示是下一毫秒时间开始对seq做随机 避免 TPS过低时 生成id 奇偶分布不均
                sequence = ThreadLocalRandom.current().nextLong(1, 3);
                currentTimestamp = tilNextMillis(lastMilliseconds);
            }
        }
        else {
            // 如果是新的ms开始，序列号置为 1 - 3 随机数
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        }

        lastMilliseconds = currentTimestamp;
        return ((currentTimestamp - EPOCH_TIME_STAMP) << TIMESTAMP_OFFSET) |
                (datacenterId << DATACENTERID_OFFSET) |
                (workerId << WORKERID_OFFSET) |
                sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = currentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimestamp();
        }
        return timestamp;
    }

    private long currentTimestamp() {
        return SystemClock.currentTimeMillis();
    }

}

