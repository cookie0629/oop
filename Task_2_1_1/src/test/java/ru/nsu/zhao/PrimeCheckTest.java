package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrimeCheckTest {

    /**
     * 测试顺序检查方法，当数组中包含非质数时，预期返回 true。
     */
    @Test
    public void testSequentialHasNonPrime_withComposite() {
        int[] compositeArray = {6, 8, 7, 13, 5, 9, 4};
        boolean result = PrimeCheck.sequentialHasNonPrime(compositeArray);
        assertTrue(result, "数组中存在非质数，顺序检查应返回 true");
    }

    /**
     * 测试顺序检查方法，当数组中全是质数时，预期返回 false。
     */
    @Test
    public void testSequentialHasNonPrime_allPrimes() {
        int[] primeArray = {
                20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149,
                6998051, 6998053
        };
        boolean result = PrimeCheck.sequentialHasNonPrime(primeArray);
        assertFalse(result, "数组全部为质数，顺序检查应返回 false");
    }

    /**
     * 测试使用 Thread 的多线程检查方法，当数组中包含非质数时，预期返回 true。
     */
    @Test
    public void testParallelHasNonPrimeWithThreads_withComposite() {
        int[] compositeArray = {6, 8, 7, 13, 5, 9, 4};
        boolean result = PrimeCheck.parallelHasNonPrimeWithThreads(compositeArray, 4);
        assertTrue(result, "数组中存在非质数，多线程检查应返回 true");
    }

    /**
     * 测试使用 Thread 的多线程检查方法，当数组中全是质数时，预期返回 false。
     */
    @Test
    public void testParallelHasNonPrimeWithThreads_allPrimes() {
        int[] primeArray = {
                20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149,
                6998051, 6998053
        };
        boolean result = PrimeCheck.parallelHasNonPrimeWithThreads(primeArray, 4);
        assertFalse(result, "数组全部为质数，多线程检查应返回 false");
    }

    /**
     * 测试使用 parallelStream 的并行检查方法，当数组中包含非质数时，预期返回 true。
     */
    @Test
    public void testParallelStreamHasNonPrime_withComposite() {
        int[] compositeArray = {6, 8, 7, 13, 5, 9, 4};
        boolean result = PrimeCheck.parallelStreamHasNonPrime(compositeArray);
        assertTrue(result, "数组中存在非质数，parallelStream 检查应返回 true");
    }

    /**
     * 测试使用 parallelStream 的并行检查方法，当数组中全是质数时，预期返回 false。
     */
    @Test
    public void testParallelStreamHasNonPrime_allPrimes() {
        int[] primeArray = {
                20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149,
                6998051, 6998053
        };
        boolean result = PrimeCheck.parallelStreamHasNonPrime(primeArray);
        assertFalse(result, "数组全部为质数，parallelStream 检查应返回 false");
    }
}
