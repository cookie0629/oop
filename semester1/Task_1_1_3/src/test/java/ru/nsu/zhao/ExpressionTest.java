package ru.nsu.zhao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class ExpressionTest {

    @Test
    public void testPrint() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        // 捕获输出进行断言
        String expected = "(3+(2*x))";
        assertEquals(expected, getPrintedExpression(e));
    }

    @Test
    public void testDerivative() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Expression de = e.derivative("x");
        // 捕获微分后的表达式输出进行断言
        String expectedDerivative = "(0+((0*x)+(2*1)))";
        assertEquals(expectedDerivative, getPrintedExpression(de));
    }

    @Test
    public void testEval() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        int result = e.eval(variables);
        assertEquals(23, result);  // 期望的计算结果是 23
    }

    @Test
    public void testSimplify() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Expression simplified = e.simplify();
        // 捕获简化后的表达式输出进行断言
        String expectedSimplified = "(3+(2*x))";  // 假设简化的表达式和原始相同
        assertEquals(expectedSimplified, getPrintedExpression(simplified));
    }

    // 辅助方法，用于捕获表达式的打印输出
    private String getPrintedExpression(Expression e) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);
        e.print();
        System.out.flush();
        System.setOut(old);
        return baos.toString();
    }
}
