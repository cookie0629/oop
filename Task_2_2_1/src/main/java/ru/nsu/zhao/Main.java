package ru.nsu.zhao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 确保配置文件路径正确
            File configFile = new File("C:\\project_oop\\oop\\Task_2_2_1\\src\\main\\java\\ru\\nsu\\zhao\\config.json");
            if (!configFile.exists()) {
                System.err.println("配置文件 config.json 不存在！");
                return;
            }
            Config config = mapper.readValue(configFile, Config.class);

            OrderQueue orderQueue = new OrderQueue();
            Warehouse warehouse = new Warehouse(config.getWarehouse_capacity());

            List<Baker> bakers = new ArrayList<>();
            for (int speed : config.getBakers()) {
                Baker baker = new Baker(orderQueue, warehouse, speed);
                bakers.add(baker);
                baker.start();
            }

            List<Courier> couriers = new ArrayList<>();
            for (int capacity : config.getCouriers()) {
                Courier courier = new Courier(warehouse, capacity);
                couriers.add(courier);
                courier.start();
            }

            // 模拟生成订单
            for (int i = 1; i <= 10; i++) {
                orderQueue.add(new Order(i));
            }

            // 关闭订单队列，停止接收新订单
            orderQueue.close();

            // 等待所有烘焙师完成工作
            for (Baker baker : bakers) {
                baker.join();
            }

            // 关闭仓库
            warehouse.shutdown();

            // 等待所有快递员完成工作
            for (Courier courier : couriers) {
                courier.join();
            }
        } catch (IOException e) {
            System.err.println("读取配置文件失败: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("线程被中断: " + e.getMessage());
        }
    }

    static class Config {
        private int[] bakers;
        private int[] couriers;
        private int warehouse_capacity;

        public int[] getBakers() {
            return bakers;
        }

        public int[] getCouriers() {
            return couriers;
        }

        public int getWarehouse_capacity() {
            return warehouse_capacity;
        }

        public void setWarehouse_capacity(int warehouse_capacity) {
            this.warehouse_capacity = warehouse_capacity;
        }

        public void setCouriers(int[] couriers) {
            this.couriers = couriers;
        }

        public void setBakers(int[] bakers) {
            this.bakers = bakers;
        }
    }
}
