package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeCheck {

    /**
     * 判断一个整数是否为质数。
     * 定义：质数必须大于 1，且只能被 1 和其本身整除。
     *
     * @param n 要判断的整数
     * @return 如果 n 是质数则返回 true，否则返回 false
     */
    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        int sqrt = (int) Math.sqrt(n);
        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 方案 1：顺序检查
     * 遍历数组，如果发现任一数字不是质数，则返回 true。
     *
     * @param arr 整数数组
     * @return 如果存在非质数则返回 true，否则返回 false
     */
    public static boolean sequentialHasNonPrime(int[] arr) {
        for (int n : arr) {
            if (!isPrime(n)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 方案 2：使用 java.lang.Thread 实现并行检查
     * 将数组划分为若干段，每个线程检查一段中的数字是否都是质数，
     * 如果任一线程发现非质数，则设置共享标志 foundComposite。
     *
     * @param arr         整数数组
     * @param threadCount 使用的线程数
     * @return 如果存在非质数则返回 true，否则返回 false
     */
    public static boolean parallelHasNonPrimeWithThreads(int[] arr, int threadCount) {
        AtomicBoolean foundComposite = new AtomicBoolean(false);
        int length = arr.length;
        Thread[] threads = new Thread[threadCount];
        // 计算每个线程处理的块大小（向上取整）
        int chunkSize = (length + threadCount - 1) / threadCount;

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, length);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end && !foundComposite.get(); j++) {
                    if (!isPrime(arr[j])) {
                        foundComposite.set(true);
                        break;
                    }
                }
            });
            threads[i].start();
        }

        // 等待所有线程结束
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // 若线程被中断则打印异常信息
                e.printStackTrace();
            }
        }

        return foundComposite.get();
    }

    /**
     * 方案 3：使用 parallelStream() 实现并行检查
     * 利用 Java 8 的并行流检查数组中是否存在非质数。
     *
     * @param arr 整数数组
     * @return 如果存在非质数则返回 true，否则返回 false
     */
    public static boolean parallelStreamHasNonPrime(int[] arr) {
        return Arrays.stream(arr)
                .parallel()
                .anyMatch(n -> !isPrime(n));
    }

    /**
     * 生成一个包含指定个数质数的数组。
     * 注意：本方法采用简单的穷举方法，适合生成中等规模的数据集，
     * 用于展示多核 CPU 在高计算量场景下的加速效果。
     *
     * @param count 需要生成的质数个数
     * @return 包含 count 个质数的数组
     */
    public static int[] generatePrimes(int count) {
        List<Integer> primes = new ArrayList<>();
        int candidate = 2;
        while (primes.size() < count) {
            if (isPrime(candidate)) {
                primes.add(candidate);
            }
            candidate++;
        }
        // 转换成 int[] 数组返回
        int[] result = new int[primes.size()];
        for (int i = 0; i < primes.size(); i++) {
            result[i] = primes.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        // ===============================
        // 示例测试（题目中给出的示例）
        // ===============================
        int[] sample1 = {6, 8, 7, 13, 5, 9, 4}; // 包含非质数，期望输出 true
        int[] sample2 = {20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149,
                6998051, 6998053}; // 全部为质数，期望输出 false

        System.out.println("【示例测试】");
        System.out.println("顺序检查 sample1: " + sequentialHasNonPrime(sample1));  // true
        System.out.println("顺序检查 sample2: " + sequentialHasNonPrime(sample2));  // false

        System.out.println("多线程检查 sample1（使用 4 个线程）: " +
                parallelHasNonPrimeWithThreads(sample1, 4));  // true
        System.out.println("多线程检查 sample2（使用 4 个线程）: " +
                parallelHasNonPrimeWithThreads(sample2, 4));  // false

        System.out.println("parallelStream 检查 sample1: " +
                parallelStreamHasNonPrime(sample1));  // true
        System.out.println("parallelStream 检查 sample2: " +
                parallelStreamHasNonPrime(sample2));  // false

        // ===============================
        // 性能测试（构造纯质数的测试数据集）
        // 用于展示在多核 CPU 下多线程/并行流带来的加速效果
        // ===============================
        // 此数据集全部为质数，因此三个方案都会遍历整个数组，
        // 有助于测量并行计算在高计算量下的加速效果。
        final int primeCount = 20000; // 可根据实际情况调整测试规模
        int[] largePrimeDataset = generatePrimes(primeCount);
        System.out.println("\n【性能测试】测试数据集： " + primeCount + " 个质数");

        // 记录顺序执行的时间
        long startTime = System.nanoTime();
        boolean seqResult = sequentialHasNonPrime(largePrimeDataset);
        long seqTime = System.nanoTime() - startTime;
        System.out.printf("顺序执行结果: %s, 用时: %.2f 毫秒%n",
                seqResult, seqTime / 1_000_000.0);

        // 对于方案 2，测试不同线程数下的执行时间
        int[] threadCounts = {1, 2, 3, 4};
        for (int threads : threadCounts) {
            startTime = System.nanoTime();
            boolean threadResult = parallelHasNonPrimeWithThreads(largePrimeDataset, threads);
            long threadTime = System.nanoTime() - startTime;
            System.out.printf("多线程（%d 线程）执行结果: %s, 用时: %.2f 毫秒%n",
                    threads, threadResult, threadTime / 1_000_000.0);
        }

        // 使用 parallelStream() 的执行时间
        startTime = System.nanoTime();
        boolean streamResult = parallelStreamHasNonPrime(largePrimeDataset);
        long streamTime = System.nanoTime() - startTime;
        System.out.printf("parallelStream 执行结果: %s, 用时: %.2f 毫秒%n",
                streamResult, streamTime / 1_000_000.0);
    }
}

