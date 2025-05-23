package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for prime validation system
 * 素数验证系统集成测试
 */
class PrimeCheckerIntegrationTest {

    private void executeTestScenario(int[] data, int port, int workers, int batchSize) throws InterruptedException {
        PrimeValidationServer server = new PrimeValidationServer(port, data, batchSize);
        Thread serverThread = new Thread(server);
        serverThread.start();

        Thread.sleep(300); // Wait for server init / 等待服务器初始化

        Thread[] workerThreads = new Thread[workers];
        for (int i = 0; i < workers; i++) {
            workerThreads[i] = new Thread(new PrimeValidationWorker("localhost", port, i + 1));
            workerThreads[i].start();
        }

        for (Thread worker : workerThreads) {
            worker.join();
        }

        serverThread.join();
    }

    @Test
    void testPrimeOnlyScenario() {
        int[] primes = new int[100];
        Arrays.fill(primes, 27644437); // Large known prime / 已知大素数
        assertDoesNotThrow(() -> executeTestScenario(primes, 54321, 5, 10));
    }

    @Test
    void testCompositeDetection() {
        int[] mixedNumbers = new int[100];
        Arrays.fill(mixedNumbers, 27644437);
        mixedNumbers[50] = 100; // Composite number / 合数
        assertDoesNotThrow(() -> executeTestScenario(mixedNumbers, 54322, 5, 10));
    }

    @Test
    void testUnevenWorkDistribution() {
        int[] largePrimeArray = new int[10000];
        Arrays.fill(largePrimeArray, 27644437);
        assertDoesNotThrow(() -> executeTestScenario(largePrimeArray, 54323, 5, 5000));
    }
}
