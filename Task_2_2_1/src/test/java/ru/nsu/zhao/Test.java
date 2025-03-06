package ru.nsu.zhao;

import org.junit.jupiter.api.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 披萨店测试类 / Pizzeria Test Class
 * 验证披萨店核心功能的单元测试 / Unit tests for core pizzeria functionality
 */
class PizzeriaTest {

    /**
     * 标准流程测试 / Standard Workflow Test
     * 验证正常开店-接单-关店流程 / Tests normal open-order-close workflow
     */
    @Test
    void testStandartWork() throws Exception {
        // 初始化披萨店 / Initialize pizzeria
        Pizzeria pizzeria = new Pizzeria("src/main/resources/test_config.json");
        pizzeria.start();  // 启动系统 / Start system

        // 模拟10个订单 / Simulate 10 orders
        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);
        }

        pizzeria.stop();  // 正常关闭 / Normal shutdown

        // 尝试关闭后接单（应被拒绝） / Attempt post-shutdown order (should be rejected)
        pizzeria.acceptOrder(11);
    }

    /**
     * 持久化关闭测试 / Shutdown with Serialization Test
     * 验证关闭时能保存未完成订单 / Tests saving pending orders during shutdown
     */
    @Test
    void testStopWithSerialization() throws Exception {
        Pizzeria pizzeria = new Pizzeria("src/main/resources/test_config.json");
        pizzeria.start();

        // 生成测试订单 / Generate test orders
        for (int i = 1; i <= 10; i++) {
            pizzeria.acceptOrder(i);
        }

        // 执行持久化关闭 / Perform serialized shutdown
        pizzeria.stopWithSerialization("src/main/resources/OldOrders");

        // 验证持久化文件存在 / Verify serialization file existence
        File file = new File("src/main/resources/OldOrders");
        assertTrue(file.exists());
    }

    /**
     * 旧订单加载测试 / Old Orders Loading Test
     * 验证从文件恢复订单的能力 / Tests order restoration from file
     */
    @Test
    void testLoadOldOrders() throws Exception {
        Pizzeria pizzeria = new Pizzeria("src/main/resources/test_config.json");
        pizzeria.start();

        // 加载历史订单 / Load historical orders
        pizzeria.loadOldOrders("src/main/resources/OldOrders");

        pizzeria.stop();  // 正常关闭系统 / Normal shutdown
    }
}
