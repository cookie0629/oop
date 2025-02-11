package ru.nsu.zhao;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 质数检查工具类，提供三种方法：
 * 1. 顺序执行
 * 2. 使用 parallelStream() 并行执行
 * 3. 使用多线程 (Threads) 并行执行
 */
public class PrimeCheck {

    /**
     * 判断一个数是否是质数。
     *
     * @param n 待检查的数
     * @return 如果是质数返回 true，否则返回 false
     */
    private static boolean is_Prime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * **顺序执行**检查数组中是否包含非质数。
     *
     * @param numbers 需要检查的整数数组
     * @return 如果数组中存在非质数，返回 true；否则返回 false
     */
    public static boolean sequentialHasNonPrime(int[] numbers) {
        for (int num : numbers) {
            if (!is_Prime(num)) return true;
        }
        return false;
    }

    /**
     * **使用 parallelStream() 并行检查**数组中是否包含非质数。
     *
     * @param numbers 需要检查的整数数组
     * @return 如果数组中存在非质数，返回 true；否则返回 false
     */
    public static boolean parallelStreamHasNonPrime(int[] numbers) {
        List<Integer> numberList = Arrays.stream(numbers).boxed().toList();
        return numberList.parallelStream().anyMatch(number -> !is_Prime(number));
    }

    /**
     * **使用多线程 (Threads) 并行检查**数组中是否包含非质数。
     *
     * @param numbers 需要检查的整数数组
     * @param threadCount 线程数量
     * @return 如果数组中存在非质数，返回 true；否则返回 false
     * @throws InterruptedException 线程被中断时抛出异常
     * @throws ExecutionException 任务执行异常
     */
    public static boolean parallelHasNonPrimeWithThreads(int[] numbers, int threadCount)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        int chunkSize = (numbers.length / threadCount) + 1; // 每个线程负责的区间大小
        Future<Boolean>[] futures = new Future[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, numbers.length);
            futures[i] = executor.submit(() -> {
                for (int j = start; j < end; j++) {
                    if (!is_Prime(numbers[j])) return true;
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
