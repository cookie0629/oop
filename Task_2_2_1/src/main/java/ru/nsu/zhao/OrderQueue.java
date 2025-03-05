package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 订单队列管理系统
 * Order queue management system
 */
public class OrderQueue {
    private final Queue<Integer> pendingOrders = new LinkedList<>();
    private final CountDownLatch readinessSignal;
    private final AtomicBoolean operationalFlag;

    public OrderQueue(CountDownLatch readinessSignal, AtomicBoolean operationalFlag) {
        this.readinessSignal = readinessSignal;
        this.operationalFlag = operationalFlag;
    }

    /**
     * 添加新订单
     * Add new order
     * @param orderID 订单编号/Order ID
     */
    public synchronized void placeOrder(int orderID) {
        pendingOrders.add(orderID);
        notifyAll();
    }

    /**
     * 获取下一个订单
     * Fetch next order
     * @return 订单编号/Order ID
     */
    public synchronized Integer fetchOrder() {
        while (pendingOrders.isEmpty() && operationalFlag.get()) {
            try {
                readinessSignal.countDown();
                wait();
            } catch (InterruptedException ignored) {}
        }
        return pendingOrders.poll();
    }

    /**
     * 检查队列状态
     * Check queue status
     */
    public synchronized boolean hasOrders() {
        return !pendingOrders.isEmpty();
    }

    public synchronized boolean isEmpty() {
        return pendingOrders.isEmpty();
    }
}