package ru.nsu.zhao;

import java.util.List;

/**
 * 快递员线程 / Courier Thread
 * 负责处理配送流程 / Handles delivery workflow
 */
public class Courier extends Thread {
    private final Warehouse warehouse;
    private final int capacity;

    /**
     * 构造函数 / Constructor
     * @param warehouse 来源仓库 / Source warehouse
     * @param capacity 运输容量 / Delivery capacity
     */
    public Courier(Warehouse warehouse, int capacity) {
        this.warehouse = warehouse;
        this.capacity = capacity;
    }

    /**
     * 线程运行逻辑 / Thread run logic
     */
    @Override
    public void run() {
        try {
            while (true) {
                List<Order> orders = warehouse.take(capacity);
                if (orders.isEmpty()) {
                    break;
                }
                for (Order order : orders) {
                    System.out.println("[" + order.getId() + "] 正在配送");
                }
                Thread.sleep(1000);
                for (Order order : orders) {
                    System.out.println("[" + order.getId() + "] 已送达客户");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
