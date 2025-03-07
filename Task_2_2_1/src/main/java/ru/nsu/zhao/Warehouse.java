package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仓库管理系统 / Warehouse Management System
 * 实现库存容量控制和线程安全操作 / Implements capacity control and thread-safe operations
 */
public class Warehouse {
    private final Queue<Order> storage = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
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
     * @param order 待存储的订单 / Order to be stored
     * @throws InterruptedException 当线程被中断时抛出 / Thrown when thread is interrupted
     */
    public void put(Order order) throws InterruptedException {
        lock.lock();
        try {
            while (storage.size() >= capacity) {
                notFull.await();
            }
            storage.add(order);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 取出披萨订单 / Retrieve pizza orders
     * @param max 最大取出数量 / Maximum number to retrieve
     * @return 订单列表 / List of orders
     * @throws InterruptedException 当线程被中断时抛出 / Thrown when thread is interrupted
     */
    public List<Order> take(int max) throws InterruptedException {
        lock.lock();
        try {
            while (storage.isEmpty() && !isStopped) {
                notEmpty.await();
            }
            if (storage.isEmpty() && isStopped) {
                return new ArrayList<>();
            }
            List<Order> orders = new ArrayList<>();
            int count = Math.min(max, storage.size());
            for (int i = 0; i < count; i++) {
                orders.add(storage.poll());
            }
            notFull.signalAll();
            return orders;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 关闭仓库 / Shutdown warehouse
     * 停止所有仓储操作 / Stop all warehouse operations
     */
    public void shutdown() {
        lock.lock();
        try {
            isStopped = true;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
