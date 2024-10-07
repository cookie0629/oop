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
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Variable " + name + " is not defined.");
        }
        return variables.get(name);
    }

    @Override
    public Expression simplify() {
        java.util.Map<String, Integer> predefinedValues = getPredefinedValues();
        if (predefinedValues != null && predefinedValues.containsKey(name)) {
            // 如果预定义值存在，直接返回其对应的数值
            return new Number(predefinedValues.get(name));
        }
        // 变量无法简化，直接返回自己
        return this;
    }

    // 获取已预定义的变量值（可以从上下文或某个配置中获取）
    private java.util.Map<String, Integer> getPredefinedValues() {
        // 这里可以从上下文中获取某些预定义的值。为了简单起见，我们暂时返回null。
        return null;
    }
}
