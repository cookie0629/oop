package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.concurrent.*;

class MainTest {

    @Test
    void testOrderQueueBasicOperations() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Order order = new Order(1);

        // Test add and take
        queue.add(order);
        assertEquals(1, queue.take().getId());

        // Test closed queue returns null
        queue.close();
        assertNull(queue.take());
    }

    @Test
    void testWarehouseCapacityBlocking() throws InterruptedException {
        Warehouse warehouse = new Warehouse(2);
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        Order order3 = new Order(3);

        warehouse.put(order1);
        warehouse.put(order2);

        // Test put blocking when full
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                warehouse.put(order3); // Should block
                fail("Should not reach here");
            } catch (InterruptedException e) {
                // Expected interruption
            }
        });

        assertThrows(TimeoutException.class, () -> future.get(500, TimeUnit.MILLISECONDS));
        executor.shutdownNow();
    }

    @Test
    void testBakerProcessOrder() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(10);
        Baker baker = new Baker(queue, warehouse, 1); // Speed 1 sec

        queue.add(new Order(1));
        queue.close();

        baker.start();
        baker.join();

        List<Order> orders = warehouse.take(10);
        assertEquals(1, orders.size());
        assertEquals(1, orders.get(0).getId());
    }

    @Test
    void testCourierDelivery() throws InterruptedException {
        Warehouse warehouse = new Warehouse(10);
        warehouse.put(new Order(1));
        warehouse.put(new Order(2));
        warehouse.shutdown();

        Courier courier = new Courier(warehouse, 2);
        courier.start();
        courier.join();

        // Verify warehouse is empty
        assertTrue(warehouse.take(1).isEmpty());
    }

    @Test
    void testFullIntegration() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(2);

        Baker baker = new Baker(queue, warehouse, 0); // Instant processing
        Courier courier = new Courier(warehouse, 2);

        queue.add(new Order(1));
        queue.add(new Order(2));
        queue.close();

        baker.start();
        courier.start();

        baker.join();
        warehouse.shutdown();
        courier.join();

        // Verify all orders processed and delivered
        assertNull(queue.take());
        assertTrue(warehouse.take(1).isEmpty());
    }
}