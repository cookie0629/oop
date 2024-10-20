package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IncidenceMatrixGraph implements Graph {
    private int[][] incidenceMatrix;
    private int vertexCount;
    private int edgeCount;
    private Map<Integer, Integer> edgeMap; // Maps edges to indices in the incidence matrix

    public IncidenceMatrixGraph(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        incidenceMatrix = new int[vertexCount][edgeCount];
        edgeMap = new HashMap<>();
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= vertexCount) {
            throw new IllegalArgumentException("Vertex index out of bounds.");
        }
    }

    @Override
    public void removeVertex(int vertex) {
        throw new UnsupportedOperationException("Remove vertex operation is not supported in Incidence Matrix.");
    }

    @Override
    public void addEdge(int from, int to) {
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[from][i] == 0 && incidenceMatrix[to][i] == 0) {
                incidenceMatrix[from][i] = 1; // From vertex
                incidenceMatrix[to][i] = -1; // To vertex
                edgeMap.put(edgeMap.size(), i);
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
                edgeMap.remove(i);
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
            List<int[]> edges = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] vertices = line.split(" ");
                int from = Integer.parseInt(vertices[0]);
                int to = Integer.parseInt(vertices[1]);
                edges.add(new int[]{from, to});
            }
            // Update vertexCount and edgeCount based on edges read
            edgeCount = edges.size();
            for (int[] edge : edges) {
                addVertex(edge[0]);
                addVertex(edge[1]);
                addEdge(edge[0], edge[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> topologicalSort() {
        int[] inDegree = new int[edgeCount];
        Map<Integer, Integer> vertexMap = new HashMap<>();

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                if (incidenceMatrix[i][j] == 1) {
                    inDegree[j]++;
                    vertexMap.put(j, i);
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < edgeCount; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> topoOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int edgeIndex = queue.poll();
            int fromVertex = vertexMap.get(edgeIndex);
            topoOrder.add(fromVertex);
            for (int j = 0; j < edgeCount; j++) {
                if (incidenceMatrix[fromVertex][j] == 1) {
                    inDegree[j]--;
                    if (inDegree[j] == 0) {
                        queue.add(j);
                    }
                }
            }
        }
        return topoOrder.size() == vertexCount ? topoOrder : null;
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
        return Arrays.deepEquals(this.incidenceMatrix, that.incidenceMatrix);
    }
}
