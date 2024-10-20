package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph implements Graph {
    private final int[][] matrix;
    private final int size;

    public AdjacencyMatrixGraph(int size) {
        this.size = size;
        matrix = new int[size][size];
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= size) {
            throw new IllegalArgumentException("Vertex out of bounds");
        }
    }

    @Override
    public void removeVertex(int vertex) {
        for (int i = 0; i < size; i++) {
            matrix[vertex][i] = 0;
            matrix[i][vertex] = 0;
        }
    }

    @Override
    public void addEdge(int from, int to) {
        matrix[from][to] = 1;
    }

    @Override
    public void removeEdge(int from, int to) {
        matrix[from][to] = 0;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (matrix[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filePath) {
        // Implement file reading logic
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyMatrixGraph that = (AdjacencyMatrixGraph) obj;
        return this.toString().equals(that.toString());
    }

    @Override
    public List<Integer> topologicalSort() {
        // Implement topological sort algorithm
        return null;
    }
}
