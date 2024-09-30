package ru.nsu.zhao;

public class HeapSort {
    public static void heapsort(int[] array) {
        int n = array.length;
        //构建树
        //Building the tree
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            // 对根节点进行堆化
            // Heapify the root node
            heapify(array, i, 0);
        }
    }
    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left] > array[largest]) {
            largest = left;
        }

        if (right < n && array[right] > array[largest]) {
            largest = right;
        }
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            // 递归堆化子树
            // Recursive heapization of subtrees
            heapify(array, n, largest);
        }
    }
}
