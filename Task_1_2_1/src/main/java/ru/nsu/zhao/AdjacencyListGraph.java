package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph implements Graph {
    private final Map<Integer, List<Integer>> adjList;

    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    @Override
    public void addVertex(int vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        adjList.values().forEach(e -> e.remove(vertex));
        adjList.remove(vertex);
    }

    @Override
    public void addEdge(int from, int to) {
        adjList.get(from).add(to);
    }

    @Override
    public void removeEdge(int from, int to) {
        adjList.get(from).remove(Integer.valueOf(to));
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjList.get(vertex);
    }

    @Override
    public void readFromFile(String filePath) {
        // Implement file reading logic
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var entry : adjList.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyListGraph that = (AdjacencyListGraph) obj;
        return adjList.equals(that.adjList);
    }

    @Override
    public List<Integer> topologicalSort() {
        // Implement topological sort algorithm
        return null;
    }
}
