package ru.nsu.zhao;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;

/**
 * 面包师类，负责根据订单队列制作披萨
 * Baker class, responsible for making pizzas based on orders from the queue
 */
public class Baker extends Thread {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final OrderQueue orderQueue;
    private final Storage storage;
    private final int speed;
    private final AtomicBoolean isOpen;
    private final CountDownLatch startLatch;

    /**
     * 构造方法，创建面包师实例
     * Constructor to create a Baker instance
     *
     * @param orderQueue 订单队列，面包师从中获取订单
     *                   Order queue from which the baker takes orders
     * @param storage    存储仓库，面包师将完成的披萨放入仓库
     *                   Storage where the baker places finished pizzas
     * @param speed      制作披萨的速度（秒）
     *                   Speed of making pizzas (in seconds)
     * @param isOpen     标志位，表示披萨店是否营业
     *                   Flag indicating whether the pizzeria is open
     * @param startLatch 启动门闩，用于同步线程启动
     *                   Start latch for synchronizing thread startup
     */
    public Baker(OrderQueue orderQueue, Storage storage, int speed, AtomicBoolean isOpen, CountDownLatch startLatch) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.orderQueue = orderQueue;
        this.storage = storage;
        this.speed = speed;
        this.isOpen = isOpen;
        this.startLatch = startLatch;
    }

    /**
     * 面包师的主要工作逻辑
     * Main working logic of the baker
     */
    @Override
    public void run() {
        while (isOpen.get() || !orderQueue.isEmpty()) {
            Integer orderId = orderQueue.takeOrder();
            if (orderId == null) continue;
            System.out.println(orderId + " Пекарь " + id + " начал готовить.");
            try {
                Thread.sleep(speed * 1000L);
            } catch (InterruptedException ignored) {}

            System.out.println(orderId + " Пекарь " + id + " закончил готовить.");
            storage.storePizza(orderId);
        }
    }

    /**
     * 安全地等待线程结束
     * Safely wait for the thread to finish
     */
    public void joinSafely() {
        try {
            join();
        } catch (InterruptedException ignored) {}
    }

    public CountDownLatch getStartLatch() {
        return startLatch;
    }
}
