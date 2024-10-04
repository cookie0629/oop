package ru.nsu.zhao;

public class Main {
    public static void main(String[] args) {
        // 创建一个表达式 (3 + (2 * x))
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

        // 打印表达式
        e.print(System.out);
        System.out.println(); // 换行

        // 对 x 进行导数
        Expression de = e.derivative("x");
        de.print(System.out);
        System.out.println(); // 换行

        // 计算表达式在 x=10 时的值
        int result = e.eval("x = 10; y = 13");
        System.out.println("Result: " + result); // 输出结果: 23

        // 简化表达式
        Expression simplified = e.simplify();
        simplified.print(System.out);
        System.out.println(); // 换行
    }
}

