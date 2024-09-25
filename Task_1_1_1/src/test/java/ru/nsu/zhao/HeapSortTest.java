package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HeapSortTest 是用于测试主要功能的类。
 */
class HeapSortTest {
    @Test
    public void testHeapSort() {
        int[] input = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};
        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortWithDuplicates() {
        int[] input = {4, 4, 3, 2, 1, 4};
        int[] expected = {1, 2, 3, 4, 4, 4};
        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5, 6, 9};
        int[] expected = {1, 2, 3, 4, 5, 6, 9};
        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortSingleElement() {
        int[] input = {1};
        int[] expected = {1};
        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortEmptyArray() {
        int[] input = {};
        int[] expected = {};
        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortWithNegativeNumbers() {
        int[] input = {-3, -1, -2, 0, 2, 1};
        int[] expected = {-3, -2, -1, 0, 1, 2};
        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortAllSameElements() {
        int[] input = {7, 7, 7, 7, 7};
        int[] expected = {7, 7, 7, 7, 7};
        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortLargeArray() {
        int n = 100000;
        int[] input = new int[n];
        int[] expected = new int[n];

        for (int i = 0; i < n; i++) {
            input[i] = n - i;
            expected[i] = i + 1;
        }

        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSortWithMixedRepeatingValues() {
        int[] input = {5, 1, 4, 2, 4, 3, 5, 1};
        int[] expected = {1, 1, 2, 3, 4, 4, 5, 5};
        HeapSort.heapsort(input);
        assertArrayEquals(expected, input);
    }
    public static void main(String[] args) {
        System.out.println();
    }
}
