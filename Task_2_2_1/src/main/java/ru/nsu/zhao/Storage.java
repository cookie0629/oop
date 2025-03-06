package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 仓库存储类，管理披萨的存储和检索
 * Storage class, manages pizza storage and retrieval
 */
public class Storage {
    private final int capacity;
    private final Queue<Integer> storage = new LinkedList<>();
    private final CountDownLatch startLatch;

    /**
     * 构造方法，创建仓库实例
     * Constructor to create a Storage instance
     *
     * @param capacity   仓库容量 / Storage capacity
     * @param startLatch 启动门闩，用于同步线程启动
     *                   Start latch for synchronizing thread startup
     */
    public Storage(int capacity, CountDownLatch startLatch) {
        this.capacity = capacity;
        this.startLatch = startLatch;
    }

    /**
     * 将披萨存入仓库
     * Store a pizza in the storage
     *
     * @param orderId 订单ID / Order ID
     */
    public synchronized void storePizza(int orderId) {
        while (storage.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        storage.add(orderId);
        System.out.println(orderId + " Пицца на складе.");
        notifyAll();
    }

    /**
     * 从仓库取出披萨
     * Take pizzas from the storage
     *
     * @param maxCount 最大取出数量 / Maximum number of pizzas to take
     * @return 取出的披萨列表 / List of taken pizzas
     */
    public synchronized List<Integer> takePizzas(int maxCount) {
        while (storage.isEmpty()) {
            try {
                startLatch.countDown();
                wait();
            } catch (InterruptedException ignored) {}
        }

        List<Integer> taken = new ArrayList<>();
        while (!storage.isEmpty() && taken.size() < maxCount) {
            taken.add(storage.poll());
        }
        notifyAll();
        return taken;
    }

    /**
     * 检查仓库是否为空
     * Check if the storage is empty
     *
     * @return true如果仓库为空，否则false
     *         true if the storage is empty, otherwise false
     */
    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }
}
