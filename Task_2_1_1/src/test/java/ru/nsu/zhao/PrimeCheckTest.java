package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * JUnit 测试类，用于测试 PrimeCheck 中的三种方法：
 * 1. sequentialHasNonPrime
 * 2. parallelStreamHasNonPrime
 * 3. parallelHasNonPrimeWithThreads（分别测试 4、8、16、32 线程）
 */
class PrimeCheckTest {
    /**
     * 性能测试：使用约一亿数据（全部为质数），比较三种方法的执行时间。
     * 数据均填充为质数 7919（遍历全部数组），测试运行时间较长，可体现多线程优势。
     */
    @Test
    void testPerformance() throws InterruptedException, ExecutionException {
        final int dataSize = 10_000_000; // 一千万数据
        final int primeNumber = 7919;    // 一个质数，确保 isPrime 执行完整的循环
        int[] numbers = new int[dataSize];
        Arrays.fill(numbers, primeNumber);
        // 预期结果：所有数均为质数，因此方法返回 false
        final boolean expected = false;

        System.out.println("====== 性能测试开始（数据量：" + dataSize + "） ======");

        // 顺序执行测试
        long start = System.currentTimeMillis();
        boolean resultSeq = PrimeCheck.sequentialHasNonPrime(numbers);
        long end = System.currentTimeMillis();
        long sequentialTime = end - start;
        System.out.println("Sequential execution time: " + sequentialTime + " ms");
        assertEquals(expected, resultSeq);

        // parallelStream() 测试
        start = System.currentTimeMillis();
        boolean resultParallelStream = PrimeCheck.parallelStreamHasNonPrime(numbers);
        end = System.currentTimeMillis();
        long parallelStreamTime = end - start;
        System.out.println("ParallelStream execution time: " + parallelStreamTime + " ms");
        assertEquals(expected, resultParallelStream);

        // 多线程测试：分别使用 4, 8, 16, 32 线程
        int[] threadCounts = {4, 8, 16, 32};
        for (int threadCount : threadCounts) {
            start = System.currentTimeMillis();
            boolean resultThreads = PrimeCheck.parallelHasNonPrimeWithThreads(numbers, threadCount);
            end = System.currentTimeMillis();
            long threadTime = end - start;
            System.out.println("Parallel execution with " + threadCount + " threads: " + threadTime + " ms");
            assertEquals(expected, resultThreads);
        }

        System.out.println("====== 性能测试结束 ======");
    }
}
