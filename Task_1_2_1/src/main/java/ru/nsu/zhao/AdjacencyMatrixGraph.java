package ru.nsu.zhao;

import java.io.*;
import java.util.*;

public class AdjacencyMatrixGraph implements Graph {
    private List<String> vertices;
    private boolean[][] adjMatrix;

    public AdjacencyMatrixGraph() {
        vertices = new ArrayList<>();
        adjMatrix = new boolean[10][10]; // 初始大小
    }

    @Override
    public void addVertex(String vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            if (vertices.size() > adjMatrix.length) {
                resizeMatrix();
            }
        }
    }

    @Override
    public void removeVertex(String vertex) {
        int index = vertices.indexOf(vertex);
        if (index != -1) {
            vertices.remove(vertex);
            for (int i = 0; i < vertices.size(); i++) {
                adjMatrix[i][index] = false;
                adjMatrix[index][i] = false;
            }
        }
    }

    @Override
    public void addEdge(String vertex1, String vertex2) {
        addVertex(vertex1);
        addVertex(vertex2);
        int index1 = vertices.indexOf(vertex1);
        int index2 = vertices.indexOf(vertex2);
        adjMatrix[index1][index2] = true;
    }

    @Override
    public void removeEdge(String vertex1, String vertex2) {
        int index1 = vertices.indexOf(vertex1);
        int index2 = vertices.indexOf(vertex2);
        if (index1 != -1 && index2 != -1) {
            adjMatrix[index1][index2] = false;
        }
    }

    @Override
    public List<String> getNeighbors(String vertex) {
        int index = vertices.indexOf(vertex);
        List<String> neighbors = new ArrayList<>();
        if (index != -1) {
            for (int i = 0; i < vertices.size(); i++) {
                if (adjMatrix[index][i]) {
                    neighbors.add(vertices.get(i));
                }
            }
        }
        return neighbors;
    }

    @Override
    public void loadFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] vertices = line.split(" ");
            if (vertices.length == 2) {
                addEdge(vertices[0], vertices[1]);
            }
        }
        reader.close();
    }

    private void resizeMatrix() {
        int newSize = adjMatrix.length * 2;
        boolean[][] newMatrix = new boolean[newSize][newSize];
        for (int i = 0; i < adjMatrix.length; i++) {
            System.arraycopy(adjMatrix[i], 0, newMatrix[i], 0, adjMatrix.length);
        }
        adjMatrix = newMatrix;
    }

    @Override
    public List<String> topologicalSort() {
        // 实现与 AdjacencyListGraph 类似
        return null; // 未实现
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AdjacencyMatrixGraph)) return false;
        AdjacencyMatrixGraph other = (AdjacencyMatrixGraph) obj;
        return Arrays.deepEquals(this.adjMatrix, other.adjMatrix) && this.vertices.equals(other.vertices);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Adjacency Matrix:\n");
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                sb.append(adjMatrix[i][j] ? "1 " : "0 ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

