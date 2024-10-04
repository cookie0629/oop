package ru.nsu.zhao;

public class Number implements Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public void print(java.io.PrintStream out) {
        out.print(value);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0); // 常数的导数为 0
    }

    @Override
    public int eval(String vars) {
        return value; // 数值直接返回
    }

    @Override
    public Expression simplify() {
        return this; // 常量已经是最简形式
    }
}
