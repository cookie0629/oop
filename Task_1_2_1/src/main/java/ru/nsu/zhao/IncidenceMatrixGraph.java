package ru.nsu.zhao;

import java.io.*;
import java.util.*;

public class IncidenceMatrixGraph implements Graph {
    private List<String> vertices;
    private List<String[]> edges; // 边的列表，每条边用两个顶点表示
    private int[][] incidenceMatrix; // 关联矩阵

    public IncidenceMatrixGraph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        incidenceMatrix = new int[10][10]; // 初始大小
    }

    @Override
    public void addVertex(String vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            resizeMatrixIfNeeded(); // 如果顶点增加，扩展矩阵
        }
    }

    @Override
    public void removeVertex(String vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) return;

        vertices.remove(vertexIndex);

        // 删除关联矩阵中的对应列
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i)[0].equals(vertex) || edges.get(i)[1].equals(vertex)) {
                edges.remove(i);
                i--; // 删除后需要调整索引
            }
        }

        resizeMatrixIfNeeded();
    }

    @Override
    public void addEdge(String vertex1, String vertex2) {
        addVertex(vertex1);
        addVertex(vertex2);

        edges.add(new String[]{vertex1, vertex2});
        resizeMatrixIfNeeded();

        int vertex1Index = vertices.indexOf(vertex1);
        int vertex2Index = vertices.indexOf(vertex2);
        int edgeIndex = edges.size() - 1;

        incidenceMatrix[vertex1Index][edgeIndex] = 1; // 顶点1连接这条边
        incidenceMatrix[vertex2Index][edgeIndex] = 1; // 顶点2也连接这条边
    }

    @Override
    public void removeEdge(String vertex1, String vertex2) {
        for (int i = 0; i < edges.size(); i++) {
            String[] edge = edges.get(i);
            if ((edge[0].equals(vertex1) && edge[1].equals(vertex2)) || (edge[0].equals(vertex2) && edge[1].equals(vertex1))) {
                edges.remove(i);
                i--;
                resizeMatrixIfNeeded();
                return;
            }
        }
    }

    @Override
    public List<String> getNeighbors(String vertex) {
        List<String> neighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) return neighbors;

        // 通过遍历关联矩阵找到所有与该顶点相关的边，并获取邻居
        for (int edgeIndex = 0; edgeIndex < edges.size(); edgeIndex++) {
            if (incidenceMatrix[vertexIndex][edgeIndex] == 1) {
                String[] edge = edges.get(edgeIndex);
                neighbors.add(edge[0].equals(vertex) ? edge[1] : edge[0]);
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

    @Override
    public List<String> topologicalSort() {
        Map<String, Integer> inDegree = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();

        for (String vertex : vertices) {
            inDegree.put(vertex, 0);
        }

        for (String[] edge : edges) {
            String from = edge[0];
            String to = edge[1];
            inDegree.put(to, inDegree.get(to) + 1);
        }

        for (String vertex : inDegree.keySet()) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            result.add(vertex);

            for (String neighbor : getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != vertices.size()) {
            throw new IllegalStateException("Graph has a cycle!");
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IncidenceMatrixGraph)) return false;
        IncidenceMatrixGraph other = (IncidenceMatrixGraph) obj;
        return this.vertices.equals(other.vertices) && this.edges.equals(other.edges);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Edges:\n");
        for (String[] edge : edges) {
            sb.append(edge[0]).append(" - ").append(edge[1]).append("\n");
        }
        sb.append("Incidence Matrix:\n");
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < edges.size(); j++) {
                sb.append(incidenceMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void resizeMatrixIfNeeded() {
        if (incidenceMatrix.length < vertices.size() || incidenceMatrix[0].length < edges.size()) {
            int newSizeVertices = Math.max(incidenceMatrix.length * 2, vertices.size());
            int newSizeEdges = Math.max(incidenceMatrix[0].length * 2, edges.size());

            int[][] newMatrix = new int[newSizeVertices][newSizeEdges];
            for (int i = 0; i < incidenceMatrix.length; i++) {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, incidenceMatrix[i].length);
            }
            incidenceMatrix = newMatrix;
        }
    }
}
