package ru.nsu.zhao;

// 表示两个表达式的乘法
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("*");
        right.print();
        System.out.print(")");
    }

    @Override
    public Expression derivative(String variable) {
        // (f * g)' = f' * g + f * g'
        return new Add(new Mul(left.derivative(variable), right), new Mul(left, right.derivative(variable)));
    }

    @Override
    //根据给定的变量值 variables 计算乘法表达式的
    public int eval(java.util.Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }

    @Override
    public Expression simplify() {
        //当前实现只是简单地递归化简左边和右边的子表达式
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();
        return new Mul(simplifiedLeft, simplifiedRight);
    }
}
