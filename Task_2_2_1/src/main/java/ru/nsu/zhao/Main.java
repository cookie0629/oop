package ru.nsu.zhao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主程序 / Main Program
 * 系统入口和配置加载 / System entry and configuration loading
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(new File("C:\\project_oop\\oop\\Task_2_2_1\\src\\main\\java\\ru\\nsu\\zhao\\config.json"), Config.class);

        // 初始化系统组件 / Initialize system components
        OrderQueue orderQueue = new OrderQueue();
        Warehouse warehouse = new Warehouse(config.getWarehouse_capacity());

        // 创建烘焙师线程 / Create baker threads
        List<Baker> bakers = new ArrayList<>();
        for (int speed : config.getBakers()) {
            Baker baker = new Baker(orderQueue, warehouse, speed);
            bakers.add(baker);
            baker.start();
        }

        // 创建快递员线程 / Create courier threads
        List<Courier> couriers = new ArrayList<>();
        for (int capacity : config.getCouriers()) {
            Courier courier = new Courier(warehouse, capacity);
            couriers.add(courier);
            courier.start();
        }

        // 生成测试订单 / Generate test orders
        for (int i = 1; i <= 10; i++) {
            orderQueue.add(new Order(i));
        }

        // 关闭订单队列 / Close order queue
        orderQueue.close();

        // 等待烘焙师完成 / Wait for bakers to finish
        for (Baker baker : bakers) {
            baker.join();
        }

        // 关闭仓库 / Shutdown warehouse
        warehouse.shutdown();

        // 等待快递员完成 / Wait for couriers to finish
        for (Courier courier : couriers) {
            courier.join();
        }
    }

    /**
     * 配置类 / Configuration Class
     * 映射JSON配置文件结构 / Maps JSON configuration structure
     */
    static class Config {
        private int[] bakers;          // 烘焙师速度配置 / Baker speed configuration
        private int[] couriers;        // 快递员容量配置 / Courier capacity configuration
        private int warehouse_capacity;// 仓库容量配置 / Warehouse capacity

        // Getter和Setter方法 / Getter and Setter methods
        public int[] getBakers() { return bakers; }
        public void setBakers(int[] bakers) { this.bakers = bakers; }
        public int[] getCouriers() { return couriers; }
        public void setCouriers(int[] couriers) { this.couriers = couriers; }
        public int getWarehouse_capacity() { return warehouse_capacity; }
        public void setWarehouse_capacity(int warehouse_capacity) {
            this.warehouse_capacity = warehouse_capacity;
        }
    }
}
