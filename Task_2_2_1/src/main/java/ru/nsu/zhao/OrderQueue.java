package ru.nsu.zhao;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 线程安全订单队列 / Thread-safe Order Queue
 * 实现生产者-消费者模式 / Implements producer-consumer pattern
 */
public class OrderQueue {
    private final Queue<Order> queue = new LinkedList<>();
    private boolean isClosed = false;

    /**
     * 添加订单到队列 / Add order to queue
     * @param order 待添加的订单 / Order to be added
     */
    public void add(Order order) {
        synchronized (this) {
            if (!isClosed) {
                queue.add(order);
                notifyAll();
            }
        }
    }

    /**
     * 从队列获取订单 / Take order from queue
     * @return 获取的订单对象 / Retrieved order object
     * @throws InterruptedException 当线程被中断时抛出 / Thrown when thread is interrupted
     */
    public Order take() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty() && !isClosed) {
                wait();
            }
            return queue.isEmpty() ? null : queue.poll();
        }
    }

    /**
     * 关闭订单队列 / Close order queue
     * 停止接收新订单 / Stop accepting new orders
     */
    public void close() {
        synchronized (this) {
            isClosed = true;
            notifyAll();
        }
    }
}