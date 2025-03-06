package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 订单队列管理类，处理订单的生产和消费
 * Order queue management class, handles order production and consumption
 */
public class OrderQueue {
    private final Queue<Integer> orders = new LinkedList<>();
    private final CountDownLatch startLatch;
    private final AtomicBoolean isOpen;

    /**
     * 构造方法，创建订单队列实例
     * Constructor to create an OrderQueue instance
     *
     * @param startLatch 启动门闩，用于同步线程启动
     *                   Start latch for synchronizing thread startup
     * @param isOpen     标志位，表示披萨店是否营业
     *                   Flag indicating whether the pizzeria is open
     */
    public OrderQueue(CountDownLatch startLatch, AtomicBoolean isOpen) {
        this.startLatch = startLatch;
        this.isOpen = isOpen;
    }

    /**
     * 添加订单到队列中
     * Add an order to the queue
     *
     * @param orderId 订单ID / Order ID
     */
    public synchronized void addOrder(int orderId) {
        orders.add(orderId);
        notifyAll();
    }

    /**
     * 从队列中取出订单
     * Take an order from the queue
     *
     * @return 订单ID，如果队列为空则返回null
     *         Order ID, or null if the queue is empty
     */
    public synchronized Integer takeOrder() {
        while (orders.isEmpty() && isOpen.get()) {
            try {
                startLatch.countDown();
                wait();
            } catch (InterruptedException ignored) {}
        }
        return orders.poll();
    }

    /**
     * 检查队列是否为空
     * Check if the queue is empty
     *
     * @return true如果队列为空，否则false
     *         true if the queue is empty, otherwise false
     */
    public synchronized boolean isEmpty() {
        return orders.isEmpty();
    }
}
