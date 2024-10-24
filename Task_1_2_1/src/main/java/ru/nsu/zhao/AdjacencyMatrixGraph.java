package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;


public class AdjacencyMatrixGraph implements Graph {
    // 邻接矩阵，用于表示图中的顶点和边
    private final List<List<Integer>> matrix;
    // 图中的顶点数量
    private final int numVertices;

    /**
     * 构造函数，初始化具有指定顶点数量的图。
     * @param vertices 图中的顶点数量
     */
    public AdjacencyMatrixGraph(int vertices) {
        this.numVertices = vertices;
        this.matrix = new ArrayList<>(vertices);

        // 初始化邻接矩阵，将每个元素设为 0，表示没有边
        for (int i = 0; i < vertices; i++) {
            List<Integer> row = new ArrayList<>(vertices);
            for (int j = 0; j < vertices; j++) {
                row.add(0); // 所有边初始化为 0
            }
            matrix.add(row);
        }
    }

    /**
     * 在两个顶点之间添加一条边。
     * @param source      源顶点
     * @param destination 目标顶点
     */
    @Override
    public void addEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) { // 检查顶点是否存在
            matrix.get(source).set(destination, 1); // 在源顶点和目标顶点之间添加边
        }
    }

    /**
     * 删除两个顶点之间的边。
     * @param source      源顶点
     * @param destination 目标顶点
     */
    @Override
    public void removeEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) { // 检查顶点是否存在
            matrix.get(source).set(destination, 0); // 删除源顶点和目标顶点之间的边
        }
    }

    /**
     * 返回指定顶点的相邻顶点列表。
     * @param vertex 要查找的顶点
     * @return 相邻顶点列表
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        if (vertex < numVertices) {
            // 遍历矩阵中的行，查找该顶点的所有邻接顶点
            for (int i = 0; i < numVertices; i++) {
                if (matrix.get(vertex).get(i) == 1) {
                    neighbors.add(i); // 添加与该顶点相邻的顶点
                }
            }
        }
        return neighbors;
    }

    /**
     * 从文件中读取图。文件格式为 "源顶点,目标顶点" 的边列表。
     * @param filePath 文件路径
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
     * 返回邻接矩阵的字符串表示。
     * @return 邻接矩阵的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t"); // 输出列索引
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n\t");
        sb.append("* ".repeat(numVertices)); // 输出分隔符
        sb.append("\n");
        // 输出矩阵的每一行和行索引
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(" * ");
            for (Integer cell : matrix.get(i)) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * 根据邻接矩阵的字符串表示检查两个图是否相等。
     * @param o 要比较的对象
     * @return 如果图相等返回 true，否则返回 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.toString().equals(o.toString()); // 比较邻接矩阵的字符串表示
    }

    /**
     * 使用 Kahn 算法对图进行拓扑排序。
     * @return 拓扑排序后的顶点列表
     * @throws GraphCycleException 如果图中存在环，无法进行拓扑排序
     */
    @Override
    public List<Integer> topologicalSort() throws GraphCycleException {
        int[] inDegree = new int[numVertices];  // 每个顶点的入度
        List<Integer> topOrder = new ArrayList<>();

        // 计算每个顶点的入度
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrix.get(i).get(j) == 1) {
                    inDegree[j]++;
                }
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

