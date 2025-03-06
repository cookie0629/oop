package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 仓库管理类 / Storage Management Class
 * 实现带容量限制的线程安全存储 / Implements thread-safe storage with capacity
 */
public class Storage {
    private final int capacity;       // 最大容量 / Maximum capacity
    private final Queue<Integer> storage = new LinkedList<>();  // 存储队列 / Storage queue
    private final CountDownLatch startLatch;  // 启动同步锁 / Startup synchronization latch

    /**
     * 构造函数 / Constructor
     * @param capacity 仓库容量 / Storage capacity
     */
    public Storage(int capacity, CountDownLatch startLatch) {
        this.capacity = capacity;
        this.startLatch = startLatch;
    }

    /**
     * 存入披萨 / Store pizza
     * @param orderId 订单ID / Order ID
     */
    public synchronized void storePizza(int orderId) {
        // 等待仓库有空位 / Wait for available space
        while (storage.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        storage.add(orderId);
        System.out.println(orderId + " Пицца на складе.");
        notifyAll();  // 通知等待线程 / Notify waiting threads
    }

    /**
     * 取出披萨 / Take pizzas
     * @param maxCount 最大取出数量 / Maximum take count
     * @return 取出的订单列表 / List of taken orders
     */
    public synchronized List<Integer> takePizzas(int maxCount) {
        // 等待仓库有货 / Wait for available pizzas
        while (storage.isEmpty()) {
            try {
                startLatch.countDown();  // 同步启动计数 / Synchronize startup count
                wait();
            } catch (InterruptedException ignored) {}
        }

        // 批量取出 / Batch retrieval
        List<Integer> taken = new ArrayList<>();
        while (!storage.isEmpty() && taken.size() < maxCount) {
            taken.add(storage.poll());
        }
        notifyAll();  // 通知生产者线程 / Notify producer threads
        return taken;
    }

    /**
     * 检查空状态 / Check empty status
     * @return 是否为空 / True if empty
     */
    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }
}