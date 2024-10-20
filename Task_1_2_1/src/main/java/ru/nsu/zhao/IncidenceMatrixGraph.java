package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph implements Graph {
    private final int[][] incidenceMatrix;
    private final int vertexCount;
    private int edgeCount;

    public IncidenceMatrixGraph(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        incidenceMatrix = new int[vertexCount][edgeCount];
    }

    @Override
    public void addVertex(int vertex) {
        // Not applicable for incidence matrix as the size is fixed
    }

    @Override
    public void removeVertex(int vertex) {
        // Not applicable for incidence matrix
    }

    @Override
    public void addEdge(int from, int to) {
        incidenceMatrix[from][edgeCount] = 1;
        incidenceMatrix[to][edgeCount] = -1;
        edgeCount++;
    }

    @Override
    public void removeEdge(int from, int to) {
        // Implement removal logic
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[vertex][i] == 1) {
                for (int j = 0; j < vertexCount; j++) {
                    if (incidenceMatrix[j][i] == -1) {
                        neighbors.add(j);
                    }
                }
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
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IncidenceMatrixGraph that = (IncidenceMatrixGraph) obj;
        return this.toString().equals(that.toString());
    }

    @Override
    public List<Integer> topologicalSort() {
        // Implement topological sort algorithm
        return null;
    }
}
