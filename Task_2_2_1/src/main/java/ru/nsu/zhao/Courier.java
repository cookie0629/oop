package ru.nsu.zhao;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;

/**
 * 快递员类，负责从仓库取货并配送披萨
 * Courier class, responsible for picking up pizzas from storage and delivering them
 */
public class Courier extends Thread {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;
    private final Storage storage;
    private final int capacity;
    private final AtomicBoolean isOpen;
    private final CountDownLatch startLatch;

    /**
     * 构造方法，创建快递员实例
     * Constructor to create a Courier instance
     *
     * @param storage    存储仓库，快递员从中取货
     *                   Storage from which the courier picks up pizzas
     * @param capacity   快递员一次能携带的披萨数量
     *                   Number of pizzas the courier can carry at once
     * @param isOpen     标志位，表示披萨店是否营业
     *                   Flag indicating whether the pizzeria is open
     * @param startLatch 启动门闩，用于同步线程启动
     *                   Start latch for synchronizing thread startup
     */
    public Courier(Storage storage, int capacity, AtomicBoolean isOpen, CountDownLatch startLatch) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.storage = storage;
        this.capacity = capacity;
        this.isOpen = isOpen;
        this.startLatch = startLatch;
    }

    /**
     * 快递员的主要工作逻辑
     * Main working logic of the courier
     */
    @Override
    public void run() {
        while (isOpen.get() || !storage.isEmpty()) {
            List<Integer> pizzas = storage.takePizzas(capacity);

            System.out.println("Курьер " + id + " забрал " + pizzas);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}

            System.out.println("Курьер " + id + " доставил " + pizzas);
        }
    }

    /**
     * 安全地等待线程结束
     * Safely wait for the thread to finish
     */
    public void joinSafely() {
        try {
            join();
        } catch (InterruptedException ignored) {}
    }
}
