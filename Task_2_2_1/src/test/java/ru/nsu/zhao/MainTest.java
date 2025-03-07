package ru.nsu.zhao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    /**
     * 测试配置文件加载 / Test configuration loading
     */
    @Test
    void testConfigLoading() throws Exception {
        // 加载测试配置文件 / Load test config
        File configFile = Paths.get("C:\\project_oop\\oop\\Task_2_2_1\\src\\main\\java\\ru\\nsu\\zhao\\config.json").toFile();
        Main.Config config = new ObjectMapper().readValue(configFile, Main.Config.class);

        // 验证配置参数 / Verify configuration values
        assertArrayEquals(new int[]{2, 3}, config.getBakers());
        assertArrayEquals(new int[]{5, 5}, config.getCouriers());
        assertEquals(10, config.getWarehouse_capacity());
    }

    /**
     * 测试完整系统流程 / Test full system workflow
     */
    @Test
    void testFullSystemWorkflow() throws Exception {
        // 重定向标准输出 / Redirect system output
        System.setOut(new java.io.PrintStream("test_output.log"));

        // 运行主程序 / Run main program
        Main.main(new String[]{});

        // 验证输出文件内容 / Verify output content
        String output = java.nio.file.Files.readString(Paths.get("test_output.log"));
        assertTrue(output.contains("已送达客户"),
                "应包含完整订单生命周期 / Should contain full order lifecycle");

        // 清理测试文件 / Cleanup test file
        java.nio.file.Files.deleteIfExists(Paths.get("test_output.log"));
    }

    /**
     * 测试线程终止机制 / Test thread termination
     */
    @Test
    void testThreadTermination() throws Exception {
        Main main = new Main();
        Main.Config config = new Main.Config();
        config.setBakers(new int[]{1});
        config.setCouriers(new int[]{2});
        config.setWarehouse_capacity(5);

        // 初始化组件 / Initialize components
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(config.getWarehouse_capacity());

        // 创建并启动线程 / Create and start threads
        List<Baker> bakers = new ArrayList<>();
        for (int speed : config.getBakers()) {
            Baker baker = new Baker(queue, warehouse, speed);
            bakers.add(baker);
            baker.start();
        }

        List<Courier> couriers = new ArrayList<>();
        for (int capacity : config.getCouriers()) {
            Courier courier = new Courier(warehouse, capacity);
            couriers.add(courier);
            courier.start();
        }

        // 添加测试订单 / Add test orders
        for (int i = 1; i <= 3; i++) {
            queue.add(new Order(i));
        }
        queue.close();

        // 等待线程结束 / Wait for threads
        for (Baker baker : bakers) {
            baker.join(2000);
            assertFalse(baker.isAlive(), "烘焙师线程应已终止 / Baker thread should be terminated");
        }

        warehouse.shutdown();

        for (Courier courier : couriers) {
            courier.join(2000);
            assertFalse(courier.isAlive(), "快递员线程应已终止 / Courier thread should be terminated");
        }
    }

    /**
     * 测试边界条件 / Test boundary conditions
     */
    @Test
    void testBoundaryConditions() throws Exception {
        // 空配置测试 / Empty config test
        Main.Config emptyConfig = new Main.Config();
        emptyConfig.setBakers(new int[]{});
        emptyConfig.setCouriers(new int[]{});
        emptyConfig.setWarehouse_capacity(0);

        // 应正常处理空配置 / Should handle empty config
        assertDoesNotThrow(() -> {
            OrderQueue queue = new OrderQueue();
            Warehouse warehouse = new Warehouse(emptyConfig.getWarehouse_capacity());
            queue.close();
            warehouse.shutdown();
        }, "应处理空配置 / Should handle empty configuration");
    }
}
