package ru.nsu.zhao;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CountDownLatch;

/**
 * 披萨店主类，协调所有组件的工作
 * Main pizzeria class, coordinates work of all components
 */
class Pizzeria {
    private final OrderQueue orderQueue;
    private final Storage storage;
    private final List<Baker> bakers = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();
    private final AtomicBoolean isOpen = new AtomicBoolean(true);
    private final CountDownLatch startLatch;

    /**
     * 构造方法，从配置文件创建披萨店实例
     * Constructor to create a Pizzeria instance from a configuration file
     *
     * @param configPath 配置文件路径 / Path to the configuration file
     * @throws Exception 如果读取配置文件时发生错误
     *                   If an error occurs while reading the configuration file
     */
    public Pizzeria(String configPath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configPath), Config.class);

        int totalWorkers = config.bakers.size() + config.couriers.size();
        this.startLatch = new CountDownLatch(totalWorkers);

        this.storage = new Storage(config.storageCapacity, startLatch);
        this.orderQueue = new OrderQueue(startLatch, isOpen);

        for (int speed : config.bakers) {
            bakers.add(new Baker(orderQueue, storage, speed, isOpen, startLatch));
        }

        for (int capacity : config.couriers) {
            couriers.add(new Courier(storage, capacity, isOpen, startLatch));
        }
    }

    /**
     * 启动披萨店，开始处理订单
     * Start the pizzeria and begin processing orders
     */
    public void start() {
        couriers.forEach(Thread::start);
        bakers.forEach(Thread::start);

        try {
            startLatch.await(); // 等待所有线程进入WAITING状态
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Пиццерия готова к приёму заказов!");
    }

    /**
     * 停止披萨店，完成所有订单后关闭
     * Stop the pizzeria and shut down after completing all orders
     */
    public void stop() {
        isOpen.set(false);
        synchronized (storage) {
            while (!orderQueue.isEmpty() || !storage.isEmpty()) {
                try {
                    storage.wait();
                } catch (InterruptedException ignored) {}
            }
        }
        bakers.forEach(Baker::joinSafely);
        couriers.forEach(Courier::joinSafely);
        System.out.println("Пиццерия завершила работу.");
    }

    /**
     * 停止披萨店并将未完成订单序列化到文件
     * Stop the pizzeria and serialize unfinished orders to a file
     *
     * @param filename 文件名 / Name of the file
     * @throws IOException 如果写入文件时发生错误
     *                     If an error occurs while writing to the file
     */
    public void stopWithSerialization(String filename) throws IOException {
        isOpen.set(false);
        List<Integer> orders = new LinkedList<>();
        while (!orderQueue.isEmpty()) {
            orders.add(orderQueue.takeOrder());
        }
        synchronized (storage) {
            while (!storage.isEmpty()) {
                try {
                    storage.wait();
                } catch (InterruptedException ignored) {}
            }
        }
        bakers.forEach(Baker::joinSafely);
        couriers.forEach(Courier::joinSafely);
        System.out.println("Пиццерия завершила работу и записала незавершённые заказы в файл: " + filename);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(orders);
        }
    }

    /**
     * 接受新订单并添加到队列中
     * Accept a new order and add it to the queue
     *
     * @param orderId 订单ID / Order ID
     */
    public void acceptOrder(int orderId) {
        if (isOpen.get()) {
            orderQueue.addOrder(orderId);
        } else {
            System.out.println(orderId + " Заказ отклонен, пиццерия закрыта.");
        }
    }

    /**
     * 从文件加载未完成订单并添加到队列中
     * Load unfinished orders from a file and add them to the queue
     *
     * @param filename 文件名 / Name of the file
     * @throws IOException            如果读取文件时发生错误
     *                                If an error occurs while reading the file
     * @throws ClassNotFoundException 如果无法找到对象的类
     *                                If the class of the object cannot be found
     */
    public void loadOldOrders(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Integer> orders = (List<Integer>) ois.readObject();
            for (int order : orders) {
                acceptOrder(order);
            }
        }
    }
}
