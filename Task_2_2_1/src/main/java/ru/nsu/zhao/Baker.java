package ru.nsu.zhao;

public class Baker extends Thread {
    private final OrderQueue orderQueue;
    private final Warehouse warehouse;
    private final int speed;

    public Baker(OrderQueue orderQueue, Warehouse warehouse, int speed) {
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = orderQueue.take();
                if (order == null) {
                    break;
                }
                System.out.println("[" + order.getId() + "] 准备中");
                Thread.sleep(speed * 1000L);
                warehouse.put(order);
                System.out.println("[" + order.getId() + "] 已存入仓库");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
