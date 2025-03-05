package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;

/**
 * 配送服务线程
 * Delivery service thread
 */
public class Courier extends Thread {
    private static final AtomicInteger COURIER_COUNTER = new AtomicInteger(1);
    private final int courierID;
    private final Storage storageArea;
    private final int deliveryCapacity;
    private final AtomicBoolean operationalFlag;
    private final CountDownLatch readinessSignal;

    /**
     * 初始化配送员
     * @param storageArea 存储区域/Storage area
     * @param capacity 配送容量/Delivery capacity
     * @param operationalFlag 运营标志/Operational flag
     * @param readinessSignal 就绪信号/Readiness signal
     */
    public Courier(Storage storageArea, int capacity,
                   AtomicBoolean operationalFlag, CountDownLatch readinessSignal) {
        this.courierID = COURIER_COUNTER.getAndIncrement();
        this.storageArea = storageArea;
        this.deliveryCapacity = capacity;
        this.operationalFlag = operationalFlag;
        this.readinessSignal = readinessSignal;
    }

    @Override
    public void run() {
        while (operationalFlag.get() || !storageArea.isEmpty()) {
            List<Integer> deliveryBatch = storageArea.retrieveItems(deliveryCapacity);

            System.out.printf("[Courier %d] PICKUP %s\n", courierID, deliveryBatch);
            try {
                Thread.sleep(3000); // 模拟配送时间/Simulate delivery time
            } catch (InterruptedException ignored) {}

            System.out.printf("[Courier %d] DELIVERED %s\n", courierID, deliveryBatch);
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