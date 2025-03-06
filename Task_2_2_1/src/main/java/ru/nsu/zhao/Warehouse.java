package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {
    private final Queue<Order> storage = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final int capacity;
    private boolean isStopped = false;

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

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
