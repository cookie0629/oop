package ru.nsu.zhao;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Prime number checking utility class, providing three methods:
 * 1. Sequential execution
 * 2. Parallel execution using parallelStream()
 * 3. Parallel execution using multiple threads
 */
public class PrimeCheck {

    /**
     * Determines whether a number is a prime number.
     * 判断一个数是否是质数。
     *
     * @param n The number to check / 待检查的数
     * @return true if the number is prime; otherwise, false / 如果是质数返回 true，否则返回 false
     */
    private static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * **Sequential execution** checks if the array contains non-prime numbers.
     * **顺序执行**检查数组中是否包含非质数。
     *
     * @param numbers The array of integers to check / 需要检查的整数数组
     * @return true if a non-prime number is found; otherwise, false / 如果数组中存在非质数，返回 true；否则返回 false
     */
    public static boolean sequentialHasComposite(int[] numbers) {
        for (int num : numbers) {
            if (!isPrime(num)) return true;
        }
        return false;
    }

    /**
     * **Parallel execution using parallelStream()** checks if the array contains non-prime numbers.
     * **使用 parallelStream() 并行检查**数组中是否包含非质数。
     *
     * @param numbers The array of integers to check / 需要检查的整数数组
     * @return true if a non-prime number is found; otherwise, false / 如果数组中存在非质数，返回 true；否则返回 false
     */
    public static boolean parallelStreamHasComposite(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !isPrime(num));
    }

    /**
     * **Parallel execution using multiple threads (Threads)** checks if the array contains non-prime numbers.
     * **使用多线程 (Threads) 并行检查**数组中是否包含非质数。
     *
     * @param numbers The array of integers to check / 需要检查的整数数组
     * @param threadCount The number of threads to use / 线程数量
     * @return true if a non-prime number is found; otherwise, false / 如果数组中存在非质数，返回 true；否则返回 false
     * @throws InterruptedException if the thread is interrupted / 当线程被中断时抛出异常
     * @throws ExecutionException if a task execution error occurs / 如果任务执行异常
     */
    public static boolean parallelHasCompositeWithThreads(int[] numbers, int threadCount)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        int chunkSize = numbers.length / threadCount;
        Future<Boolean>[] futures = new Future[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = (i == threadCount - 1) ? numbers.length : start + chunkSize;
            futures[i] = executor.submit(() -> {
                for (int j = start; j < end; j++) {
                    if (!isPrime(numbers[j])) return true;
                }
                return false;
            });
        }

        executor.shutdown();
        for (Future<Boolean> future : futures) {
            if (future.get()) return true;
        }
        return false;
    }
}

