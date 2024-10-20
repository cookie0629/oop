package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AdjacencyMatrixGraph implements Graph {
    private int[][] matrix;
    private int size;

    public AdjacencyMatrixGraph(int size) {
        this.size = size;
        matrix = new int[size][size];
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= size) {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex >= size) {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }
        for (int i = 0; i < size; i++) {
            matrix[vertex][i] = 0;
            matrix[i][vertex] = 0;
        }
    }

    @Override
    public void addEdge(int from, int to) {
        if (from >= size || to >= size) {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }
        matrix[from][to] = 1;
    }

    @Override
    public void removeEdge(int from, int to) {
        if (from >= size || to >= size) {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }
        matrix[from][to] = 0;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (vertex >= size) {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                for (int col = 0; col < values.length; col++) {
                    matrix[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> topologicalSort() {
        int[] inDegree = new int[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 1) {
                    inDegree[j]++;
                }
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> topoOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            topoOrder.add(vertex);
            for (int i = 0; i < size; i++) {
                if (matrix[vertex][i] == 1) {
                    inDegree[i]--;
                    if (inDegree[i] == 0) {
                        queue.add(i);
                    }
                }
            }
        }
        return topoOrder.size() == size ? topoOrder : null;
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.matrix[i][j] != that.matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
