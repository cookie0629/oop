package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 仓库管理系统 / Warehouse Management System
 * 实现库存容量控制和线程安全操作 / Implements capacity control and thread-safe operations
 */
public class Warehouse {
    private final Queue<Order> storage = new LinkedList<>();
    private final int capacity;
    private boolean isStopped = false;

    /**
     * 仓库构造函数 / Warehouse constructor
     * @param capacity 最大存储容量 / Maximum storage capacity
     */
    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 存入披萨订单 / Store pizza order
     *
     * @param order 待存储的订单 / Order to be stored
     * @throws InterruptedException 当线程被中断时抛出 / Thrown when thread is interrupted
     */
    public synchronized void put(Order order) throws InterruptedException {
        while (storage.size() >= capacity) {
            wait();
        }
        storage.add(order);
        notifyAll();
    }

    /**
     * 取出披萨订单 / Retrieve pizza orders
     *
     * @param max 最大取出数量 / Maximum number to retrieve
     * @return 订单列表 / List of orders
     * @throws InterruptedException 当线程被中断时抛出 / Thrown when thread is interrupted
     */
    public synchronized List<Order> take(int max) throws InterruptedException {
        while (storage.isEmpty() && !isStopped) {
            wait();
        }
        if (storage.isEmpty() && isStopped) {
            return new ArrayList<>();
        }
        List<Order> orders = new ArrayList<>();
        int count = Math.min(max, storage.size());
        for (int i = 0; i < count; i++) {
            orders.add(storage.poll());
        }
        notifyAll();
        return orders;
    }

    /**
     * 关闭仓库 / Shutdown warehouse
     * 停止所有仓储操作 / Stop all warehouse operations
     */
    public synchronized void shutdown() {
        isStopped = true;
        notifyAll();
    }
}
