package ru.nsu.zhao;
import java.util.*;

public class AdjacencyListGraph implements Graph {
    private final Map<String, List<String>> adjacencyList;

    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
    }

    // 添加顶点
    @Override
    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    // 删除顶点
    @Override
    public void removeVertex(String vertex) {
        adjacencyList.values().forEach(e -> e.remove(vertex));
        adjacencyList.remove(vertex);
    }

    // 添加边
    @Override
    public void addEdge(String vertex1, String vertex2) {
        adjacencyList.get(vertex1).add(vertex2);
    }

    // 删除边
    @Override
    public void removeEdge(String vertex1, String vertex2) {
        adjacencyList.get(vertex1).remove(vertex2);
    }

    // 获取邻居
    @Override
    public List<String> getNeighbors(String vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    // 从文件中读取图
    @Override
    public void readFromFile(String filePath) {
        // 实现从文件读取图的逻辑
    }

    // 拓扑排序
    @Override
    public List<String> topologicalSort() {
        List<String> sorted = new ArrayList<>();
        Map<String, Integer> inDegree = new HashMap<>();
        for (String v : adjacencyList.keySet()) {
            inDegree.put(v, 0);
        }

        // 计算所有顶点的入度
        for (String v : adjacencyList.keySet()) {
            for (String neighbor : adjacencyList.get(v)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<String> queue = new LinkedList<>();
        for (String v : inDegree.keySet()) {
            if (inDegree.get(v) == 0) {
                queue.add(v);
            }
        }

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            sorted.add(vertex);
            for (String neighbor : adjacencyList.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sorted.size() != adjacencyList.size()) {
            throw new RuntimeException("图中存在环！");
        }

        return sorted;
    }

    // 输出图的字符串表示
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency List Graph:\n");
        for (String vertex : adjacencyList.keySet()) {
            sb.append(vertex).append(": ").append(adjacencyList.get(vertex)).append("\n");
        }
        return sb.toString();
    }

    // 检查两个图是否相等
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyListGraph that = (AdjacencyListGraph) obj;
        return Objects.equals(adjacencyList, that.adjacencyList);
    }
}

