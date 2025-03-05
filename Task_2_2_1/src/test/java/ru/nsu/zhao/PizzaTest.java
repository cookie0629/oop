package ru.nsu.zhao;

import org.junit.jupiter.api.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {
    private Pizzeria pizzeria;

    @BeforeEach
    void setUp() throws Exception {
        pizzeria = new Pizzeria("src/main/resources/test_config.json");
    }

    @AfterEach
    void tearDown() {
        pizzeria.shutdown();
    }

    @org.junit.jupiter.api.Test
    void testStandardWork() throws Exception {
        pizzeria.launch();
        for (int i = 1; i <= 10; i++) {
            pizzeria.orderSystem.placeOrder(i);
        }
        assertFalse(pizzeria.orderSystem.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testStorageOperations() {
        Storage storage = new Storage(5, new CountDownLatch(1));
        storage.storeItem(1);
        storage.storeItem(2);
        assertEquals(2, storage.retrieveItems(3).size());
        assertTrue(storage.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testOrderQueueOperations() {
        OrderQueue orderQueue = new OrderQueue(new CountDownLatch(1), new AtomicBoolean(true));
        orderQueue.placeOrder(100);
        assertEquals(100, orderQueue.fetchOrder());
        assertNull(orderQueue.fetchOrder());
    }
}
