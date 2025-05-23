package ru.nsu.zhao;

import java.io.Serializable;

/**
 * Data container for number batches
 * 数字批次数据容器
 */
public class NumberBatch implements Serializable {
    public final int[] numberArray;

    public NumberBatch(int[] numbers) {
        this.numberArray = numbers;
    }
}
