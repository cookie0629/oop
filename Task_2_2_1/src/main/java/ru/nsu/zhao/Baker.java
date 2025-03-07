package ru.nsu.zhao;

/**
 * 烘焙师线程 / Baker Thread
 * 负责处理订单制作流程 / Handles order processing workflow
 */
public class Baker extends Thread {
    private final OrderQueue orderQueue;
    private final Warehouse warehouse;
    private final int speed;

    /**
     * 构造函数 / Constructor
     * @param orderQueue 订单队列 / Order queue
     * @param warehouse 目标仓库 / Target warehouse
     * @param speed 制作速度（秒） / Production speed in seconds
     */
    public Baker(OrderQueue orderQueue, Warehouse warehouse, int speed) {
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.speed = speed;
    }

    /**
     * 线程运行逻辑 / Thread run logic
     */
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
