package ru.nsu.zhao;

import java.util.*;

// 使用三种方式表示图：邻接矩阵、关联矩阵和邻接列表
class Graph {
    private final Map<String, Integer> vertexIndexMap; // 用于映射顶点到索引
    private final List<String> indexVertexMap; // 反向映射索引到顶点
    private boolean[][] adjMatrix; // 邻接矩阵
    private boolean[][] incidenceMatrix; // 关联矩阵
    private final Map<String, List<String>> adjList; // 邻接列表
    private int vertexCount;
    private int edgeCount;

    // 构造函数
    public Graph() {
        vertexIndexMap = new HashMap<>();
        indexVertexMap = new ArrayList<>();
        adjList = new HashMap<>();
        vertexCount = 0;
        edgeCount = 0;
    }

    // 添加顶点
    public void addVertex(String vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            vertexIndexMap.put(vertex, vertexCount);
            indexVertexMap.add(vertex);
            adjList.put(vertex, new ArrayList<>());
            vertexCount++;
            updateMatrices();
        }
    }

    // 添加边
    public void addEdge(String vertex1, String vertex2) {
        addVertex(vertex1);
        addVertex(vertex2);
        int index1 = vertexIndexMap.get(vertex1);
        int index2 = vertexIndexMap.get(vertex2);

        // 邻接矩阵和邻接列表的更新
        adjMatrix[index1][index2] = true;
        adjMatrix[index2][index1] = true;
        adjList.get(vertex1).add(vertex2);
        adjList.get(vertex2).add(vertex1);

        // 关联矩阵更新
        incidenceMatrix[edgeCount][index1] = true;
        incidenceMatrix[edgeCount][index2] = true;
        edgeCount++;
        updateMatrices();
    }

    // 删除顶点
    public void removeVertex(String vertex) {
        if (vertexIndexMap.containsKey(vertex)) {
            int index = vertexIndexMap.get(vertex);

            // 邻接列表删除
            for (String neighbor : adjList.get(vertex)) {
                adjList.get(neighbor).remove(vertex);
            }
            adjList.remove(vertex);

            // 邻接矩阵和关联矩阵清理
            for (int i = 0; i < vertexCount; i++) {
                adjMatrix[index][i] = false;
                adjMatrix[i][index] = false;
            }
            vertexIndexMap.remove(vertex);
            updateMatrices();
        }
    }

    // 获取顶点的邻居列表
    public List<String> getNeighbors(String vertex) {
        return adjList.getOrDefault(vertex, new ArrayList<>());
    }

    // 更新邻接矩阵和关联矩阵大小
    private void updateMatrices() {
        adjMatrix = new boolean[vertexCount][vertexCount];
        incidenceMatrix = new boolean[edgeCount][vertexCount];
    }

    // 比较两个图是否相等
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Graph other)) return false;
        return this.adjList.equals(other.adjList); // 使用邻接表比较
    }

    // 图的字符串输出
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency List:\n");
        for (String vertex : adjList.keySet()) {
            sb.append(vertex).append(" -> ").append(adjList.get(vertex)).append("\n");
        }
        sb.append("Adjacency Matrix:\n");
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                sb.append(adjMatrix[i][j] ? "1 " : "0 ");
            }
            sb.append("\n");
        }
        sb.append("Incidence Matrix:\n");
        for (int i = 0; i < edgeCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                sb.append(incidenceMatrix[i][j] ? "1 " : "0 ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // 拓扑排序算法 (Kahn算法)
    public List<String> topologicalSort() {
        Map<String, Integer> inDegree = new HashMap<>();
        for (String vertex : adjList.keySet()) {
            inDegree.put(vertex, 0);
        }

        // 计算每个顶点的入度
        for (String vertex : adjList.keySet()) {
            for (String neighbor : adjList.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        // 创建一个队列用于保存入度为0的顶点
        Queue<String> queue = new LinkedList<>();
        for (String vertex : inDegree.keySet()) {
            if (inDegree.get(vertex) == 0) {
                queue.offer(vertex);
            }
        }

        List<String> topoOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            topoOrder.add(vertex);

            for (String neighbor : adjList.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (topoOrder.size() != adjList.size()) {
            throw new IllegalStateException("Graph has a cycle!");
        }

        return topoOrder;
    }

    // 主函数用于测试
    public static void main(String[] args) {
        Graph graph = new Graph();

        // 添加顶点和边
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");
        graph.addEdge("D", "A");

        // 打印图结构
        System.out.println(graph);

        // 执行拓扑排序
        try {
            List<String> topoOrder = graph.topologicalSort();
            System.out.println("Topological Sorting: " + topoOrder);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        // 比较两个图
        Graph graph2 = new Graph();
        graph2.addEdge("A", "B");
        graph2.addEdge("B", "C");
        graph2.addEdge("A", "C");
        graph2.addEdge("D", "A");
        System.out.println("Graph equals graph2: " + graph.equals(graph2));
    }
}
