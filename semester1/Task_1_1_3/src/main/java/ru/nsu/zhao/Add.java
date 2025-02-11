package ru.nsu.zhao;

// 表示两个表达式的加法
public class Add extends Expression {
    private final Expression left;
    private final Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("+");
        right.print();
        System.out.print(")");
    }

    @Override
    public Expression derivative(String variable) {
        // (f + g)' = f' + g'
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(java.util.Map<String, Integer> variables) {
        return left.eval(variables) + right.eval(variables);
    }

    @Override
    public Expression simplify() {
        // 先简化左右两侧的表达式
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        // 否则返回简化后的加法表达式
        return new Add(simplifiedLeft, simplifiedRight);
    }
}
