package ru.nsu.zhao;

public interface Expression {
    void print(java.io.PrintStream out);
    Expression derivative(String var) throws IllegalArgumentException;
    int eval(String vars) throws IllegalArgumentException;
    Expression simplify();
}
