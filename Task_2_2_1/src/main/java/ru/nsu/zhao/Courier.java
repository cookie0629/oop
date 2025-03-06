package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 快递员线程类 / Courier Thread Class
 * 从仓库取货并配送 / Takes pizzas from storage and delivers
 */
public class Courier extends Thread {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;            // 快递员ID / Courier ID
    private final Storage storage;   // 存储仓库 / Storage facility
    private final int capacity;      // 最大携带量 / Maximum carrying capacity
    private final AtomicBoolean isOpen;  // 营业状态标志 / Shop open status flag
    private final CountDownLatch startLatch; // 启动同步锁 / Startup synchronization latch

    /**
     * 构造函数 / Constructor
     * @param storage 存储仓库 / Storage facility
     * @param capacity 携带容量 / Carrying capacity
     * @param isOpen 营业状态标志 / Shop open status flag
     * @param startLatch 启动同步锁 / Startup synchronization latch
     */
    public Courier(Storage storage, int capacity,
                   AtomicBoolean isOpen, CountDownLatch startLatch) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.storage = storage;
        this.capacity = capacity;
        this.isOpen = isOpen;
        this.startLatch = startLatch;
    }

    /**
     * 主运行逻辑 / Main run logic
     * 循环配送直到店铺关闭且仓库清空 / Delivers until shop closes and storage empties
     */
    @Override
    public void run() {
        while (isOpen.get() || !storage.isEmpty()) {
            List<Integer> pizzas = storage.takePizzas(capacity);
            System.out.println("Курьер " + id + " забрал " + pizzas);

            try {
                Thread.sleep(3000);  // 模拟配送时间 / Simulate delivery time
            } catch (InterruptedException ignored) {}

            System.out.println("Курьер " + id + " доставил " + pizzas);
        }
    }

    /**
     * 安全关闭线程 / Safely terminate thread
     */
    public void joinSafely() {
        try {
            join();  // 等待线程结束 / Wait for thread termination
        } catch (InterruptedException ignored) {}
    }

    public CountDownLatch getStartLatch() {
        return startLatch;
    }
}
