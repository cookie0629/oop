package ru.nsu.zhao;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CountDownLatch;

/**
 * 披萨店运营管理系统
 * Pizzeria operation management system
 */
public class Pizzeria {
    final OrderQueue orderSystem;
    private final Storage storageSystem;
    private final List<Baker> bakerTeam = new ArrayList<>();
    private final List<Courier> courierTeam = new ArrayList<>();
    private final AtomicBoolean operationalStatus = new AtomicBoolean(true);
    private final CountDownLatch initSignal;

    /**
     * 初始化披萨店
     * @param configFile 配置文件路径/Configuration file path
     */
    public Pizzeria(String configFile) throws Exception {
        ObjectMapper jsonParser = new ObjectMapper();
        Config settings = jsonParser.readValue(new File(configFile), Config.class);

        int totalWorkers = settings.bakerSpeeds.size() + settings.courierCapacities.size();
        this.initSignal = new CountDownLatch(totalWorkers);

        this.storageSystem = new Storage(settings.maxStorage, initSignal);
        this.orderSystem = new OrderQueue(initSignal, operationalStatus);

        for (int speed : settings.bakerSpeeds) {
            bakerTeam.add(new Baker(orderSystem, storageSystem, speed, operationalStatus, initSignal));
        }

        for (int capacity : settings.courierCapacities) {
            courierTeam.add(new Courier(storageSystem, capacity, operationalStatus, initSignal));
        }
    }

    /**
     * 启动系统
     * Start system
     */
    public void launch() {
        courierTeam.forEach(Thread::start);
        bakerTeam.forEach(Thread::start);

        try {
            initSignal.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("System READY");
    }

    /**
     * 安全关闭系统
     * Safe shutdown
     */
    public void shutdown() {
        operationalStatus.set(false);
        synchronized (storageSystem) {
            while (orderSystem.hasOrders() || !storageSystem.isEmpty()) {
                try {
                    storageSystem.wait();
                } catch (InterruptedException ignored) {}
            }
        }
        bakerTeam.forEach(Baker::shutdown);
        courierTeam.forEach(Courier::shutdown);
        System.out.println("System SHUTDOWN");
    }
}