package ru.nsu.zhao;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全订单队列 / Thread-safe Order Queue
 * 实现生产者-消费者模式 / Implements producer-consumer pattern
 */
public class OrderQueue {
    private final Queue<Order> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean isClosed = false;

    /**
     * 添加订单到队列 / Add order to queue
     * @param order 待添加的订单 / Order to be added
     */
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

    /**
     * 从队列获取订单 / Take order from queue
     * @return 获取的订单对象 / Retrieved order object
     * @throws InterruptedException 当线程被中断时抛出 / Thrown when thread is interrupted
     */
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

    /**
     * 关闭订单队列 / Close order queue
     * 停止接收新订单 / Stop accepting new orders
     */
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
