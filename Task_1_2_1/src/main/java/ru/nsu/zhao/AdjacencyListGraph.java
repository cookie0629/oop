package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
        adjList.values().forEach(e -> e.remove((Integer) vertex));
        adjList.remove(vertex);
    }

    @Override
    public void addEdge(int from, int to) {
        adjList.get(from).add(to);
    }

    @Override
    public void removeEdge(int from, int to) {
        adjList.get(from).remove((Integer) to);
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjList.get(vertex);
    }

    @Override
    public void readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] vertices = line.split(" ");
                int from = Integer.parseInt(vertices[0]);
                int to = Integer.parseInt(vertices[1]);
                addVertex(from);
                addVertex(to);
                addEdge(from, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> topologicalSort() {
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int vertex : adjList.keySet()) {
            inDegree.put(vertex, 0);
        }
        for (List<Integer> neighbors : adjList.values()) {
            for (int neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int vertex : inDegree.keySet()) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        List<Integer> topoOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            topoOrder.add(vertex);
            for (int neighbor : adjList.getOrDefault(vertex, Collections.emptyList())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        return topoOrder.size() == adjList.size() ? topoOrder : null;
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
        return this.adjList.equals(that.adjList);
    }
}
