package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {

    // 测试订单队列基本操作
    @Test
    void testOrderQueueBasicOperations() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Order order = new Order(1);

        // 测试添加和取出订单
        queue.add(order);
        assertEquals(order.getId(), queue.take().getId());

        // 测试队列关闭
        queue.close();
        assertNull(queue.take());
    }

    // 测试仓库容量限制
    @Test
    void testWarehouseCapacity() throws InterruptedException {
        Warehouse warehouse = new Warehouse(2);

        // 填充仓库至容量上限
        warehouse.put(new Order(1));
        warehouse.put(new Order(2));

        // 测试超额存储（需异步验证）
        Thread thread = new Thread(() -> {
            try {
                warehouse.put(new Order(3));
            } catch (InterruptedException e) {
                fail();
            }
        });
        thread.start();

        // 等待线程进入阻塞状态
        Thread.sleep(100);
        assertEquals(Thread.State.WAITING, thread.getState());

        // 清理测试线程
        thread.interrupt();
    }

    // 测试完整业务流程
    @Test
    void testFullWorkflow() throws InterruptedException {
        // 重定向标准输出
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // 初始化系统组件
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(5);

        // 创建生产者和消费者
        Baker baker = new Baker(queue, warehouse, 1);
        Courier courier = new Courier(warehouse, 3);

        // 启动线程
        baker.start();
        courier.start();

        // 添加测试订单
        queue.add(new Order(1001));
        queue.add(new Order(1002));
        queue.close();

        // 等待流程完成
        baker.join(3000);
        warehouse.shutdown();
        courier.join(3000);

        // 验证输出结果
        String result = output.toString();
        assertTrue(result.contains("[1001] 准备中"));
        assertTrue(result.contains("[1001] 已存入仓库"));
        assertTrue(result.contains("[1001] 正在配送"));
        assertTrue(result.contains("[1001] 已送达客户"));
    }

    // 测试多烘焙师协作
    @Test
    void testMultipleBakers() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(10);

        // 创建两个不同速度的烘焙师
        Baker fastBaker = new Baker(queue, warehouse, 1);
        Baker slowBaker = new Baker(queue, warehouse, 2);

        fastBaker.start();
        slowBaker.start();

        // 添加测试订单
        queue.add(new Order(2001));
        queue.add(new Order(2002));
        queue.close();

        // 等待处理完成
        Thread.sleep(2500);
        warehouse.shutdown();

        // 验证仓库处理结果
        assertEquals(0, warehouse.take(10).size());
    }

    // 测试快递员容量限制
    @Test
    void testCourierCapacity() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);
        Courier courier = new Courier(warehouse, 2);

        // 填充仓库
        warehouse.put(new Order(3001));
        warehouse.put(new Order(3002));
        warehouse.put(new Order(3003));

        // 启动快递员
        courier.start();
        Thread.sleep(1500); // 等待配送完成

        // 验证剩余库存
        assertEquals(1, warehouse.take(3).size());
        warehouse.shutdown();
    }
}
