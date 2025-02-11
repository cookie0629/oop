package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;


public class AdjacencyListGraph implements Graph {
    // 邻接表，存储图中每个顶点的相邻顶点列表
    private final List<List<Integer>> adjList;
    // 图中的顶点数量
    private final int numVertices;

    /**
     * 构造函数，初始化具有指定顶点数量的图。
     * @param vertices 图中的顶点数量
     *
     */
    public AdjacencyListGraph(int vertices) {
        this.numVertices = vertices;
        this.adjList = new ArrayList<>(vertices);

        // 初始化邻接表，为每个顶点创建一个空的相邻顶点列表
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    /**
     * 在两个顶点之间添加有向边。
     * @param source      源顶点
     *
     * @param destination 目标顶点
     */
    @Override
    public void addEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) { // 检查顶点是否存在
            adjList.get(source).add(destination); // 在源顶点的邻接表中添加目标顶点
        }
    }

    /**
     * 删除两个顶点之间的有向边。
     * @param source      源顶点
     *
     * @param destination 目标顶点
     */
    @Override
    public void removeEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) { // 检查顶点是否存在
            adjList.get(source).remove((Integer) destination); // 删除对应的边
        }
    }

    /**
     * 返回指定顶点的相邻顶点列表。
     * @param vertex 要查找的顶点
     *
     * @return 相邻顶点列表
     *
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (vertex < numVertices) {
            return new ArrayList<>(adjList.get(vertex)); // 返回相邻顶点的副本，防止修改原始列表
        }
        return new ArrayList<>(); // 如果顶点不存在，返回空列表
    }

    /**
     * 从文件中读取图的结构。文件格式为 "源顶点,目标顶点" 的边列表。
     * @param filePath 文件路径
     *
     * @throws Exception 如果无法读取文件
     */
    @Override
    public void readFromFile(String filePath) throws Exception {
        try (FileReader fileReader = new FileReader(filePath)) {
            Scanner scannerFile = new Scanner(fileReader);
            String line = scannerFile.nextLine();
            String[] edges = line.split(" ");
            for (String edge : edges) {
                String[] pair = edge.split(",");
                this.addEdge(Integer.parseInt(pair[0]), Integer.parseInt(pair[1])); // 添加边
            }
        } catch (Exception e){
            throw new Exception(e.getMessage()); // 抛出异常
        }
    }

    /**
     * 返回图的邻接表的字符串表示。
     * @return 图的字符串表示
     *
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(": ").append(adjList.get(i)).append("\n"); // 构建邻接表的字符串表示
        }
        return sb.toString();
    }

    /**
     * 根据图的字符串表示检查两个图是否相等。
     * @param o 要比较的对象
     *
     * @return 如果图相等返回 true，否则返回 false
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.toString().equals(o.toString()); // 比较图的字符串表示
    }

    /**
     * 使用 Kahn 算法对图进行拓扑排序。
     * @return 拓扑排序后的顶点列表
     *
     * @throws GraphCycleException 如果图中存在环，无法进行拓扑排序
     */
    @Override
    public List<Integer> topologicalSort() throws GraphCycleException {
        int[] inDegree = new int[numVertices];  // 每个顶点的入度
        List<Integer> topOrder = new ArrayList<>();

        // 计算每个顶点的入度
        for (int i = 0; i < numVertices; i++) {
            for (int neighbor : adjList.get(i)) {
                inDegree[neighbor]++;
            }
        }

        // 找出所有入度为 0 的顶点
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < numVertices; i++) {
            if (inDegree[i] == 0) {
                stack.push(i);
            }
        }

        // Kahn 算法
        while (!stack.isEmpty()) {
            int current = stack.pop();
            topOrder.add(current);

            // 减少所有相邻顶点的入度
            for (int neighbor : getNeighbors(current)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    stack.push(neighbor);
                }
            }
        }

        // 检查是否存在环
        if (topOrder.size() != numVertices) {
            throw new GraphCycleException("图中存在环，无法进行拓扑排序。");
        }

        return topOrder; // 返回拓扑排序后的顶点列表
    }
}

