package ru.nsu.zhao;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CountDownLatch;

/**
 * 披萨店主控类 / Pizzeria Main Controller
 * 管理整个披萨店运营流程 / Manages entire pizzeria operations
 */
class Pizzeria {
    // 组件声明 / Component declarations
    private final OrderQueue orderQueue;    // 订单队列 / Order queue
    private final Storage storage;         // 存储仓库 / Storage
    private final List<Baker> bakers;      // 面包师列表 / List of bakers
    private final List<Courier> couriers;  // 快递员列表 / List of couriers
    private final AtomicBoolean isOpen = new AtomicBoolean(true); // 营业状态 / Open status
    private final CountDownLatch startLatch; // 启动同步锁 / Startup synchronization latch

    /**
     * 构造函数（通过配置文件） / Constructor (with config file)
     * @param configPath 配置文件路径 / Configuration file path
     */
    public Pizzeria(String configPath) throws Exception {
        // 加载配置文件 / Load configuration
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configPath), Config.class);

        // 初始化同步锁 / Initialize synchronization latch
        int totalWorkers = config.bakers.size() + config.couriers.size();
        this.startLatch = new CountDownLatch(totalWorkers);

        // 初始化组件 / Initialize components
        this.storage = new Storage(config.storageCapacity, startLatch);
        this.orderQueue = new OrderQueue(startLatch, isOpen);

        // 创建工作人员 / Create workers
        this.bakers = new ArrayList<>();
        this.couriers = new ArrayList<>();

        for (int speed : config.bakers) {
            bakers.add(new Baker(orderQueue, storage, speed, isOpen, startLatch));
        }
        for (int capacity : config.couriers) {
            couriers.add(new Courier(storage, capacity, isOpen, startLatch));
        }
    }

    /**
     * 启动披萨店 / Start pizzeria
     */
    public void start() {
        couriers.forEach(Thread::start);  // 启动快递员 / Start couriers
        bakers.forEach(Thread::start);    // 启动面包师 / Start bakers

        try {
            startLatch.await();  // 等待所有线程就绪 / Wait for all threads ready
            System.out.println("Пиццерия готова к приёму заказов!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 停止运营（常规） / Stop operations (normal)
     */
    public void stop() {
        isOpen.set(false);  // 设置关闭标志 / Set closed flag

        // 等待订单处理完成 / Wait for order processing
        synchronized (storage) {
            while (!orderQueue.isEmpty() || !storage.isEmpty()) {
                try {
                    storage.wait();
                } catch (InterruptedException ignored) {}
            }
        }

        // 关闭所有线程 / Terminate all threads
        bakers.forEach(Baker::joinSafely);
        couriers.forEach(Courier::joinSafely);
        System.out.println("Пиццерия завершила работу.");
    }

    /**
     * 带持久化的停止 / Stop with serialization
     * @param filename 持久化文件名 / Serialization file name
     */
    public void stopWithSerialization(String filename) throws IOException {
        isOpen.set(false);
        List<Integer> pendingOrders = new LinkedList<>();

        // 收集未处理订单 / Collect pending orders
        while (!orderQueue.isEmpty()) {
            pendingOrders.add(orderQueue.takeOrder());
        }

        // 等待仓库清空 / Wait for storage empty
        synchronized (storage) {
            while (!storage.isEmpty()) {
                try {
                    storage.wait();
                } catch (InterruptedException ignored) {}
            }
        }

        // 终止线程 / Terminate threads
        bakers.forEach(Baker::joinSafely);
        couriers.forEach(Courier::joinSafely);

        // 序列化未完成订单 / Serialize pending orders
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(pendingOrders);
        }
        System.out.println("Пиццерия завершила работу и записала незавершённые заказы в файл: " + filename);
    }

    /**
     * 接收新订单 / Accept new order
     * @param orderId 订单ID / Order ID
     */
    public void acceptOrder(int orderId) {
        if (isOpen.get()) {
            orderQueue.addOrder(orderId);  // 添加到队列 / Add to queue
        } else {
            System.out.println(orderId + " Заказ отклонен, пиццерия закрыта.");
        }
    }

    /**
     * 加载历史订单 / Load old orders
     * @param filename 订单文件名 / Order file name
     */
    public void loadOldOrders(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Integer> orders = (List<Integer>) ois.readObject();
            orders.forEach(this::acceptOrder);  // 重新提交订单 / Resubmit orders
        }
    }
}
