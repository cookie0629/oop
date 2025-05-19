package ru.nsu.zhao;

// 表示一个常量数字
public class Number extends Expression {
    private final int value;
    public Number(int value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    @Override
    public Expression derivative(String variable) {
        // 常量的积分为 0
        return new Number(0);
    }

    @Override
    public int eval(java.util.Map<String, Integer> variables) {
        return value;
    }

    @Override
    public Expression simplify() {
        // 常量无法再简化，直接返回自己
        return this;
    }
}
