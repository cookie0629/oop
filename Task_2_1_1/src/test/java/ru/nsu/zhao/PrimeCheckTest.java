package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ExecutionException;

class PrimeCheckTest {

    @Test
    void testSequentialHasNonPrime() {
        int[] compositeArray = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(PrimeCheck.sequentialHasNonPrime(compositeArray));

        int[] primeArray = {20319251, 6997901, 6997927, 6997937, 17858849};
        assertFalse(PrimeCheck.sequentialHasNonPrime(primeArray));
    }

    @Test
    void testParallelStreamHasNonPrime() {
        int[] compositeArray = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(PrimeCheck.parallelStreamHasNonPrime(compositeArray));

        int[] primeArray = {20319251, 6997901, 6997927, 6997937, 17858849};
        assertFalse(PrimeCheck.parallelStreamHasNonPrime(primeArray));
    }

    @Test
    void testParallelHasNonPrimeWithThreads() throws ExecutionException, InterruptedException {
        int[] compositeArray = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(PrimeCheck.parallelHasNonPrimeWithThreads(compositeArray, 4));

        int[] primeArray = {20319251, 6997901, 6997927, 6997937, 17858849};
        assertFalse(PrimeCheck.parallelHasNonPrimeWithThreads(primeArray, 4));
    }
}
