package ru.nsu.zhao;

import java.io.*;
import java.util.*;

public class AdjacencyListGraph implements Graph {
    private Map<String, List<String>> adjList;

    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    @Override
    public void addVertex(String vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(String vertex) {
        adjList.values().forEach(e -> e.remove(vertex)); // 移除与该顶点的所有边
        adjList.remove(vertex);
    }

    @Override
    public void addEdge(String vertex1, String vertex2) {
        addVertex(vertex1);
        addVertex(vertex2);
        adjList.get(vertex1).add(vertex2);
    }

    @Override
    public void removeEdge(String vertex1, String vertex2) {
        List<String> neighbors = adjList.get(vertex1);
        if (neighbors != null) {
            neighbors.remove(vertex2);
        }
    }

    @Override
    public List<String> getNeighbors(String vertex) {
        return adjList.getOrDefault(vertex, new ArrayList<>());
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

        for (String vertex : adjList.keySet()) {
            inDegree.put(vertex, 0);
        }

        for (String vertex : adjList.keySet()) {
            for (String neighbor : adjList.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        for (String vertex : inDegree.keySet()) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            result.add(vertex);

            for (String neighbor : adjList.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != adjList.size()) {
            throw new IllegalStateException("Graph has a cycle!");
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AdjacencyListGraph)) return false;
        AdjacencyListGraph other = (AdjacencyListGraph) obj;
        return this.adjList.equals(other.adjList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String vertex : adjList.keySet()) {
            sb.append(vertex).append(" -> ").append(adjList.get(vertex)).append("\n");
        }
        return sb.toString();
    }
}
