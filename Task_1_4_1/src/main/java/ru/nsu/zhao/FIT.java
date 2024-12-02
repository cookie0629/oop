package ru.nsu.zhao;

/**
 * Represents a grade with possible values
 * 表示具有可能值的等级
 */
public enum FIT {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    UNSATISFACTORY(2);

    private final int value;

    FIT(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
