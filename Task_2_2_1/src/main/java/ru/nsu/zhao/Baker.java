package ru.nsu.zhao;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;

/**
 * 面包师线程类 / Baker Thread Class
 * 从订单队列获取订单并制作披萨 / Takes orders from queue and makes pizzas
 */
public class Baker extends Thread {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;                 // 面包师ID / Baker ID
    private final OrderQueue orderQueue;  // 订单队列 / Order queue
    private final Storage storage;        // 存储仓库 / Storage facility
    private final int speed;              // 制作速度（秒/个） / Cooking speed in seconds
    private final AtomicBoolean isOpen;   // 店铺营业状态 / Shop open status
    private final CountDownLatch startLatch; // 启动同步锁 / Startup synchronization latch

    /**
     * 构造函数 / Constructor
     * @param orderQueue 订单队列 / Order queue
     * @param storage 存储仓库 / Storage facility
     * @param speed 制作速度（秒） / Cooking speed in seconds
     * @param isOpen 营业状态标志 / Shop open status flag
     * @param startLatch 启动同步锁 / Startup synchronization latch
     */
    public Baker(OrderQueue orderQueue, Storage storage, int speed,
                 AtomicBoolean isOpen, CountDownLatch startLatch) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.orderQueue = orderQueue;
        this.storage = storage;
        this.speed = speed;
        this.isOpen = isOpen;
        this.startLatch = startLatch;
    }

    /**
     * 主运行逻辑 / Main run logic
     * 循环处理订单直到店铺关闭且队列清空 / Processes orders until shop closes and queue empties
     */
    @Override
    public void run() {
        while (isOpen.get() || !orderQueue.isEmpty()) {
            Integer orderId = orderQueue.takeOrder();
            if (orderId == null) continue;
            System.out.println(orderId + " Пекарь " + id + " начал готовить.");
            try {
                Thread.sleep(speed * 1000L);  // 模拟制作时间 / Simulate cooking time
            } catch (InterruptedException ignored) {}

            System.out.println(orderId + " Пекарь " + id + " закончил готовить.");
            storage.storePizza(orderId);  // 存入仓库 / Store to storage
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
