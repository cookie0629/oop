package ru.nsu.zhao;

// 表示一个变量
public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public Expression derivative(String variable) {
        // 自己的导数为 1，否则为 0
        return variable.equals(name) ? new Number(1) : new Number(0);
    }

    @Override
    public int eval(java.util.Map<String, Integer> variables) {
        // 如果变量未定义，返回 0 作为默认值
        return variables.getOrDefault(name, 0);
    }

    @Override
    public Expression simplify() {
        // 变量无法简化，直接返回自己
        return this;
    }
}
