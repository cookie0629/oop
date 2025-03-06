package ru.nsu.zhao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class PizzaTest {

    private OrderQueue orderQueue;
    private Warehouse warehouse;
    private Baker baker;
    private Courier courier;

    @BeforeEach
    void setUp() {
        orderQueue = new OrderQueue();
        warehouse = new Warehouse(5); // 仓库容量为5
        baker = new Baker(orderQueue, warehouse, 2); // 烘焙师速度为2秒
        courier = new Courier(warehouse, 3); // 快递员容量为3
    }

    @Test
    void testOrderQueueAddAndTake() throws InterruptedException {
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        // 添加订单
        orderQueue.add(order1);
        orderQueue.add(order2);

        // 取出订单
        assertEquals(order1.getId(), orderQueue.take().getId());
        assertEquals(order2.getId(), orderQueue.take().getId());
    }

    @Test
    void testOrderQueueClose() throws InterruptedException {
        orderQueue.close();

        // 队列关闭后，尝试取出订单应返回null
        assertNull(orderQueue.take());
    }

    @Test
    void testWarehousePutAndTake() throws InterruptedException {
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        // 存入仓库
        warehouse.put(order1);
        warehouse.put(order2);

        // 从仓库取出
        List<Order> orders = warehouse.take(2);
        assertEquals(2, orders.size());
        assertEquals(order1.getId(), orders.get(0).getId());
        assertEquals(order2.getId(), orders.get(1).getId());
    }

    @Test
    void testWarehouseShutdown() throws InterruptedException {
        warehouse.shutdown();

        // 仓库关闭后，尝试取出订单应返回空列表
        assertTrue(warehouse.take(3).isEmpty());
    }

    @Test
    void testBaker() throws InterruptedException {
        Order order = new Order(1);
        orderQueue.add(order);
        baker.start();

        // 等待烘焙师处理订单
        Thread.sleep(3000); // 烘焙师速度为2秒，等待3秒确保完成

        // 检查仓库中是否有订单
        List<Order> orders = warehouse.take(1);
        assertEquals(1, orders.size());
        assertEquals(order.getId(), orders.get(0).getId());
    }

    @Test
    void testCourier() throws InterruptedException {
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        warehouse.put(order1);
        warehouse.put(order2);
        courier.start();

        // 等待快递员处理订单
        Thread.sleep(2000); // 快递员配送时间为1秒，等待2秒确保完成

        // 检查仓库是否为空
        assertTrue(warehouse.take(1).isEmpty());
    }
}