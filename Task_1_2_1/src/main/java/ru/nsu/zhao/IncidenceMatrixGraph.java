package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

/**
 * 使用关联矩阵表示图的类。
 */
public class IncidenceMatrixGraph implements Graph {
    private final List<List<Integer>> matrix;  // 关联矩阵
    private int numVertices;  // 顶点数量
    private int numEdges;  // 边的数量

    /**
     * 构造函数，初始化具有指定顶点数量的图。
     * @param vertices 图中的顶点数量
     */
    public IncidenceMatrixGraph(int vertices) {
        this.numVertices = vertices;
        this.numEdges = 0;
        this.matrix = new ArrayList<>(vertices);

        // 初始化每个顶点对应的空行
        for (int i = 0; i < vertices; i++) {
            List<Integer> row = new ArrayList<>();
            matrix.add(row);
        }
    }

    /**
     * 在两个顶点之间添加一条边。
     * @param source      起始顶点
     * @param destination 目标顶点
     */
    @Override
    public void addEdge(int source, int destination) {
        if (source < numVertices && destination < numVertices) {  // 检查顶点是否存在
            // 为新边添加一个列
            for (int i = 0; i < numVertices; i++) {
                matrix.get(i).add(0);  // 初始化新列为0
            }

            matrix.get(source).set(numEdges, 1);  // 设置起始顶点
            matrix.get(destination).set(numEdges, 2);  // 设置目标顶点
            numEdges++;
        }
    }

    /**
     * 删除两个顶点之间的边。
     * @param source      起始顶点
     * @param destination 目标顶点
     */
    @Override
    public void removeEdge(int source, int destination) {
        for (int j = 0; j < numEdges; j++) {
            if (matrix.get(source).get(j) == 1 && matrix.get(destination).get(j) == 2) {
                for (int i = 0; i < numVertices; i++) {
                    matrix.get(i).remove(j);  // 删除对应的列
                }
                numEdges--;
                break;
            }
        }
    }

    /**
     * 返回指定顶点的邻接顶点列表。
     * @param vertex 要查找邻居的顶点
     * @return 邻接顶点的列表
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        if (vertex < numVertices) {
            for (int j = 0; j < numEdges; j++) {
                if (matrix.get(vertex).get(j) == 1) {  // 如果顶点是边的起点
                    for (int i = 0; i < numVertices; i++) {
                        if (matrix.get(i).get(j) == 2) {  // 找到边的终点
                            neighbors.add(i);
                            break;
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * 从文件中读取图的结构。文件格式为："起点,终点"。
     * @param filePath 文件路径
     * @throws Exception 如果文件无法读取
     */
    @Override
    public void readFromFile(String filePath) throws Exception {
        try (FileReader fileReader = new FileReader(filePath)) {
            Scanner scannerFile = new Scanner(fileReader);
            String line = scannerFile.nextLine();
            String[] edges = line.split(" ");
            for (String edge : edges) {
                String[] pair = edge.split(",");
                this.addEdge(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 返回图的关联矩阵的字符串表示形式。
     * @return 图的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        for (int i = 0; i < numEdges; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n\t");
        sb.append("* ".repeat(numEdges));
        sb.append("\n");
        for (int i = 0; i < numVertices; i++) {
            sb.append(i).append(" * ");
            for (int j = 0; j < numEdges; j++) {
                sb.append(matrix.get(i).get(j)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * 比较两个图是否相等。
     * @param o 要比较的对象
     * @return 如果图相等，则返回 true，否则返回 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.toString().equals(o.toString());
    }

    /**
     * 对图进行拓扑排序（Kahn 算法）。
     * @return 顶点的拓扑排序列表
     * @throws GraphCycleException 如果图中存在环，排序不可能
     */
    @Override
    public List<Integer> topologicalSort() throws GraphCycleException {
        int[] inDegree = new int[numVertices];  // 每个顶点的入度
        List<Integer> topOrder = new ArrayList<>();

        // 计算每个顶点的入度
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numEdges; j++) {
                if (matrix.get(i).get(j) == 2) {
                    inDegree[i]++;
                }
            }
        }

        // 找到所有入度为0的顶点
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

            // 减少所有邻居的入度
            for (int neighbor : getNeighbors(current)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    stack.push(neighbor);
                }
            }
        }

        // 检查是否有环
        if (topOrder.size() != numVertices) {
            throw new GraphCycleException("图中存在环，无法进行拓扑排序。");
        }

        return topOrder;
    }
}

