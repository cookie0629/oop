package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 仓储管理系统
 * Storage management system
 */
public class Storage {
    private final int maxCapacity;
    private final Queue<Integer> inventory = new ArrayDeque<>();
    private final CountDownLatch readinessSignal;

    public Storage(int maxCapacity, CountDownLatch readinessSignal) {
        this.maxCapacity = maxCapacity;
        this.readinessSignal = readinessSignal;
    }

    /**
     * 存入物品
     * Store item
     * @param itemID 物品编号/Item ID
     */
    public synchronized void storeItem(int itemID) {
        while (inventory.size() >= maxCapacity) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        inventory.add(itemID);
        System.out.printf("[Storage] + Item#%d\n", itemID);
        notifyAll();
    }

    /**
     * 提取物品
     * Retrieve items
     * @param maxAmount 最大提取量/Max retrieval amount
     * @return 物品列表/Item list
     */
    public synchronized List<Integer> retrieveItems(int maxAmount) {
        while (inventory.isEmpty()) {
            try {
                readinessSignal.countDown();
                wait();
            } catch (InterruptedException ignored) {}
        }

        List<Integer> result = new ArrayList<>();
        while (!inventory.isEmpty() && result.size() < maxAmount) {
            result.add(inventory.poll());
        }
        notifyAll();
        return result;
    }

    /**
     * 检查库存状态
     * Check inventory status
     */
    public synchronized boolean isEmpty() {
        return inventory.isEmpty();
    }
}