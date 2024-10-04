package ru.nsu.zhao;

public class Mul implements Expression {
    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(java.io.PrintStream out) {
        out.print("(");
        left.print(out);
        out.print(" * ");
        right.print(out);
        out.print(")");
    }

    @Override
    public Expression derivative(String var) {
        // 乘法的导数使用乘法法则
        return new Add(
                new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var))
        );
    }

    @Override
    public int eval(String vars) throws IllegalArgumentException {
        return left.eval(vars) * right.eval(vars);
    }

    @Override
    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();
        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(((Number) simplifiedLeft).eval("") * ((Number) simplifiedRight).eval(""));
        }
        if (simplifiedLeft instanceof Number && ((Number) simplifiedLeft).eval("") == 0 ||
                simplifiedRight instanceof Number && ((Number) simplifiedRight).eval("") == 0) {
            return new Number(0); // 乘以 0 的化简
        }
        if (simplifiedLeft instanceof Number && ((Number) simplifiedLeft).eval("") == 1) {
            return simplifiedRight; // 乘以 1 的化简
        }
        if (simplifiedRight instanceof Number && ((Number) simplifiedRight).eval("") == 1) {
            return simplifiedLeft;
        }
        return new Mul(simplifiedLeft, simplifiedRight);
    }
}
