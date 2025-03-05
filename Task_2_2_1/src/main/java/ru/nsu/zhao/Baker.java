package ru.nsu.zhao;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;

/**
 * 披萨制作线程
 * Pizza preparation thread
 */
public class Baker extends Thread {
    private static final AtomicInteger BAKER_COUNTER = new AtomicInteger(1);
    private final int bakerID;
    private final OrderQueue orderPool;
    private final Storage storageArea;
    private final int processDuration; // 制作耗时（秒）in seconds
    private final AtomicBoolean operationalFlag;
    private final CountDownLatch readinessSignal;

    /**
     * 初始化面包师
     * @param orderPool 订单队列/Order queue
     * @param storageArea 存储区域/Storage area
     * @param speed 制作速度/Preparation speed
     * @param operationalFlag 运营标志/Operational flag
     * @param readinessSignal 就绪信号/Readiness signal
     */
    public Baker(OrderQueue orderPool, Storage storageArea, int speed,
                 AtomicBoolean operationalFlag, CountDownLatch readinessSignal) {
        this.bakerID = BAKER_COUNTER.getAndIncrement();
        this.orderPool = orderPool;
        this.storageArea = storageArea;
        this.processDuration = speed;
        this.operationalFlag = operationalFlag;
        this.readinessSignal = readinessSignal;
    }

    @Override
    public void run() {
        while (operationalFlag.get() || !orderPool.isEmpty()) {
            Integer currentOrder = orderPool.fetchOrder();
            if (currentOrder == null) continue;

            System.out.printf("[Baker %d] START Order#%d\n", bakerID, currentOrder);
            try {
                Thread.sleep(processDuration * 1000L);
            } catch (InterruptedException ignored) {}

            System.out.printf("[Baker %d] FINISH Order#%d\n", bakerID, currentOrder);
            storageArea.storeItem(currentOrder);
        }
    }

    /**
     * 安全终止线程
     * Safely terminate thread
     */
    public void shutdown() {
        try {
            join();
        } catch (InterruptedException ignored) {}
    }

    public CountDownLatch getReadinessSignal() {
        return readinessSignal;
    }
}