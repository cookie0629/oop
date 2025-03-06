package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 线程安全订单队列 / Thread-safe Order Queue
 * 管理订单的生产消费流程 / Manages order production-consumption flow
 */
public class OrderQueue {
    private final Queue<Integer> orders = new LinkedList<>();  // 订单队列 / Order queue
    private final CountDownLatch startLatch;  // 启动同步锁 / Startup synchronization latch
    private final AtomicBoolean isOpen;       // 营业状态标志 / Shop open status flag

    /**
     * 构造函数 / Constructor
     * @param startLatch 启动同步锁 / Startup synchronization latch
     * @param isOpen 营业状态标志 / Shop open status flag
     */
    public OrderQueue(CountDownLatch startLatch, AtomicBoolean isOpen) {
        this.startLatch = startLatch;
        this.isOpen = isOpen;
    }

    /**
     * 添加订单 / Add order
     * @param orderId 订单ID / Order ID
     */
    public synchronized void addOrder(int orderId) {
        orders.add(orderId);
        notifyAll();  // 唤醒等待线程 / Wake up waiting threads
    }

    /**
     * 获取订单 / Take order
     * @return 订单ID 或null / Order ID or null
     */
    public synchronized Integer takeOrder() {
        while (orders.isEmpty() && isOpen.get()) {
            try {
                startLatch.countDown();  // 同步启动计数 / Synchronize startup count
                wait();  // 等待新订单 / Wait for new orders
            } catch (InterruptedException ignored) {}
        }
        return orders.poll();  // 返回并移除队列头 / Retrieve and remove head of queue
    }

    /**
     * 检查队列空状态 / Check empty status
     * @return 是否为空 / True if empty
     */
    public synchronized boolean isEmpty() {
        return orders.isEmpty();
    }
}
