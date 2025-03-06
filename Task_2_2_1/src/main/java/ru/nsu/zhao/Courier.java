package ru.nsu.zhao;

import java.util.List;

public class Courier extends Thread {
    private final Warehouse warehouse;
    private final int capacity;

    public Courier(Warehouse warehouse, int capacity) {
        this.warehouse = warehouse;
        this.capacity = capacity;
    }

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
