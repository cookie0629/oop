package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph implements Graph {
    private int[][] incidenceMatrix;
    private int vertexCount;
    private int edgeCount;

    public IncidenceMatrixGraph(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        incidenceMatrix = new int[vertexCount][edgeCount];
    }

    @Override
    public void addVertex(int vertex) {
        // 在关联矩阵中，顶点数目是固定的，所以无法动态添加
        throw new UnsupportedOperationException("Vertex count is fixed in Incidence Matrix.");
    }

    @Override
    public void removeVertex(int vertex) {
        throw new UnsupportedOperationException("Remove vertex operation is not supported in Incidence Matrix.");
    }

    @Override
    public void addEdge(int from, int to) {
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[from][i] == 0 && incidenceMatrix[to][i] == 0) {
                incidenceMatrix[from][i] = 1;
                incidenceMatrix[to][i] = -1;
                break;
            }
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[from][i] == 1 && incidenceMatrix[to][i] == -1) {
                incidenceMatrix[from][i] = 0;
                incidenceMatrix[to][i] = 0;
                break;
            }
        }
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");
                for (int col = 0; col < values.length; col++) {
                    incidenceMatrix[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                if (this.incidenceMatrix[i][j] != that.incidenceMatrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
