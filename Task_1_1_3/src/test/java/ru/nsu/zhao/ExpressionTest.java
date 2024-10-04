package ru.nsu.zhao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ExpressionTest {

    @Test
    public void testAddExpression() {
        // 测试加法表达式
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(3 + (2 * x))", captureOutput(e)); // 检查输出是否符合预期

        // 测试 eval 函数
        int result = e.eval("x = 10");
        assertEquals(23, result); // 3 + 2 * 10 = 23

        // 测试导数
        Expression derivative = e.derivative("x");
        assertEquals("(0 + ((0 * x) + (2 * 1)))", captureOutput(derivative));

        // 测试简化
        Expression simplified = e.simplify();
        assertEquals("(3 + (2 * x))", captureOutput(simplified));
    }

    @Test
    public void testMulExpression() {
        // 测试乘法表达式
        Expression e = new Mul(new Number(3), new Variable("y"));
        assertEquals("(3 * y)", captureOutput(e));

        // 测试 eval 函数
        int result = e.eval("y = 4");
        assertEquals(12, result); // 3 * 4 = 12

        // 测试导数
        Expression derivative = e.derivative("y");
        assertEquals("(3 * 1)", captureOutput(derivative));

        // 测试简化
        Expression simplified = e.simplify();
        assertEquals("(3 * y)", captureOutput(simplified));
    }

    private String captureOutput(Expression expression) {
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        expression.print(new java.io.PrintStream(out));
        return out.toString().trim();
    }
}
