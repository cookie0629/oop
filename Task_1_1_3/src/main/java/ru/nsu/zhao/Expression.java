package ru.nsu.zhao;

// 抽象类，定义基本的表达式接口
public abstract class Expression {
    // 打印表达式
    public abstract void print();

    // 对表达式进行符号微分
    public abstract Expression derivative(String variable);

    // 计算表达式的值
    public abstract int eval(java.util.Map<String, Integer> variables);

    // 简化表达式
    public abstract Expression simplify();
}
