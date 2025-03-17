package ru.nsu.zhao;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 线程安全订单队列 / Thread-safe Order Queue
 * 基于 synchronized/wait/notify 实现生产者-消费者模式
 */
public class OrderQueue {
    private final Queue<Order> queue = new LinkedList<>();
    private boolean isClosed = false;

    /**
     * 添加订单到队列 / Add order to queue
     * @param order 待添加的订单 / Order to be added
     */
    public synchronized void add(Order order) {
        if (isClosed) {
            // 队列已关闭，拒绝新订单
            return;
        }
        queue.add(order);
        notifyAll(); // 唤醒可能等待的消费者线程
    }

    /**
     * 从队列获取订单 / Take order from queue
     * @return 订单对象，若队列关闭且为空则返回 null
     * @throws InterruptedException 当线程被中断时抛出
     */
    public synchronized Order take() throws InterruptedException {
        // 循环检查防止虚假唤醒
        while (queue.isEmpty() && !isClosed) {
            wait(); // 队列为空且未关闭时等待
        }
        // 队列关闭且为空时返回 null，否则返回订单
        return queue.isEmpty() ? null : queue.poll();
    }

    /**
     * 关闭订单队列 / Close order queue
     * 停止接收新订单，并唤醒所有等待线程
     */
    public synchronized void close() {
        isClosed = true;
        notifyAll(); // 唤醒所有等待的消费者线程
    }
}
