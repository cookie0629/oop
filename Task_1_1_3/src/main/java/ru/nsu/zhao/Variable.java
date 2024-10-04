package ru.nsu.zhao;

import java.util.Map;
import java.util.HashMap;

public class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void print(java.io.PrintStream out) {
        out.print(name);
    }

    @Override
    public Expression derivative(String var) {
        if (name.equals(var)) {
            return new Number(1); // 自身变量的导数为 1
        } else {
            return new Number(0); // 其他变量的导数为 0
        }
    }

    @Override
    public int eval(String vars) throws IllegalArgumentException {
        Map<String, Integer> variables = parseVars(vars);
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            throw new IllegalArgumentException("Variable " + name + " not found.");
        }
    }

    @Override
    public Expression simplify() {
        return this; // 变量不能被简化
    }

    private Map<String, Integer> parseVars(String vars) {
        Map<String, Integer> variables = new HashMap<>();
        String[] assignments = vars.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.trim().split("=");
            if (parts.length == 2) {
                variables.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            }
        }
        return variables;
    }
}
