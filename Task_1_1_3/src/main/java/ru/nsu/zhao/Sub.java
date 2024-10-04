package ru.nsu.zhao;

// 表示两个表达式的减法
public class Sub extends Expression {
    private Expression left;
    private Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("-");
        right.print();
        System.out.print(")");
    }

    @Override
    public Expression derivative(String variable) {
        // (f - g)' = f' - g'
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(java.util.Map<String, Integer> variables) {
        return left.eval(variables) - right.eval(variables);
    }

    @Override
    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            int result = ((Number) simplifiedLeft).eval(null) - ((Number) simplifiedRight).eval(null);
            return new Number(result);
        }

        return new Sub(simplifiedLeft, simplifiedRight);
    }
}
