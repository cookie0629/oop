package ru.nsu.zhao;

// 用于测试上述功能的测试类
import java.util.HashMap;
import java.util.Map;

public class TestExpression {
    public static void main(String[] args) {
        // 创建表达式: 3 + (2 * x)
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

        // 打印表达式
        System.out.print("表达式: ");
        e.print();
        System.out.println();

        // 对 x 进行微分
        Expression de = e.derivative("x");
        System.out.print("对 x 微分: ");
        de.print();
        System.out.println();

        // 计算表达式的值 (x = 10)
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        int result = e.eval(variables);
        System.out.println("计算结果 (x = 10): " + result);

        // 简化表达式
        Expression simplified = e.simplify();
        System.out.print("简化后的表达式: ");
        simplified.print();
        System.out.println();
    }
}


