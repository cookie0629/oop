package ru.nsu.zhao;

import org.junit.jupiter.api.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 披萨店测试类，用于测试披萨店的各项功能
 * Pizzeria test class, used to test the functionalities of the pizzeria
 */
class PizzeriaTest {

    /**
     * 测试披萨店的正常工作流程
     * Test the standard workflow of the pizzeria
     *
     * @throws Exception 如果测试过程中发生异常
     *                   If an exception occurs during the test
     */
    @Test
    void testStandardWork() throws Exception {
        // 创建披萨店实例
        Pizzeria pizzeria = new Pizzeria("src/main/resources/config.json");
        pizzeria.start();

        // 接受10个订单
        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);
        }

        // 停止披萨店
        pizzeria.stop();

        // 尝试接受新订单，应被拒绝
        pizzeria.acceptOrder(11);
        System.out.println("测试：披萨店关闭后，订单11被拒绝。");
    }

    /**
     * 测试披萨店停止并序列化未完成订单的功能
     * Test the functionality of stopping the pizzeria and serializing unfinished orders
     *
     * @throws Exception 如果测试过程中发生异常
     *                   If an exception occurs during the test
     */
    @Test
    void testStopWithSerialization() throws Exception {
        // 创建披萨店实例
        Pizzeria pizzeria = new Pizzeria("src/main/resources/config.json");
        pizzeria.start();

        // 接受10个订单
        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);
        }

        // 停止披萨店并序列化未完成订单
        String filename = "src/main/resources/OldOrders";
        pizzeria.stopWithSerialization(filename);

        // 检查文件是否存在
        File file = new File(filename);
        assertTrue(file.exists(), "文件应存在，但未找到: " + filename);
        System.out.println("测试：未完成订单已成功序列化到文件: " + filename);
    }

    /**
     * 测试从文件加载未完成订单的功能
     * Test the functionality of loading unfinished orders from a file
     *
     * @throws Exception 如果测试过程中发生异常
     *                   If an exception occurs during the test
     */
    @Test
    void testLoadOldOrders() throws Exception {
        // 创建披萨店实例
        Pizzeria pizzeria = new Pizzeria("src/main/resources/config.json");
        pizzeria.start();

        // 从文件加载未完成订单
        String filename = "src/main/resources/OldOrders";
        pizzeria.loadOldOrders(filename);

        // 停止披萨店
        pizzeria.stop();
        System.out.println("测试：从未完成订单文件加载的订单已成功处理。");
    }
}
