package ru.nsu.zhao;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderQueue {
    private final Queue<Order> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean isClosed = false;

    public void add(Order order) {
        lock.lock();
        try {
            if (!isClosed) {
                queue.add(order);
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public Order take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty() && !isClosed) {
                condition.await();
            }
            return queue.isEmpty() ? null : queue.poll();
        } finally {
            lock.unlock();
        }
    }

    public void close() {
        lock.lock();
        try {
            isClosed = true;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}