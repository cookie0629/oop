package ru.nsu.zhao;

public class Add implements Expression {
    private final Expression left;
    private final Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(java.io.PrintStream out) {
        out.print("(");
        left.print(out);
        out.print(" + ");
        right.print(out);
        out.print(")");
    }

    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var)); // 和的导数为每项的导数之和
    }

    @Override
    public int eval(String vars) throws IllegalArgumentException {
        return left.eval(vars) + right.eval(vars);
    }

    @Override
    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();
        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(((Number) simplifiedLeft).eval("") + ((Number) simplifiedRight).eval(""));
        }
        return new Add(simplifiedLeft, simplifiedRight);
    }
}
