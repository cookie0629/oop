package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 使用邻接矩阵实现图的类。
 *
 * @param <T> 图中顶点存储元素的类型
 */
public class AdjacencyMatrixGraph<T> implements Graph<T> {
    private final List<Vertex<T>> verticesList; // 存储顶点的列表
    private final Map<Vertex<T>, Integer> vertexIndexMap; // 顶点与索引的映射
    private boolean[][] matrix; // 邻接矩阵
    private int capacity; // 当前容量

    /**
     * 构造一个新的空邻接矩阵图，初始容量为 10。
     */
    public AdjacencyMatrixGraph() {
        verticesList = new ArrayList<>(); // 初始化顶点列表
        vertexIndexMap = new HashMap<>(); // 初始化顶点索引映射
        capacity = 10; // 初始容量
        matrix = new boolean[capacity][capacity]; // 创建邻接矩阵
    }

    /**
     * 向图中添加一个顶点。
     * 如果当前容量已满，则扩展邻接矩阵的容量。
     *
     * @param vertex 要添加的顶点
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        if (verticesList.size() >= capacity) {
            expandMatrix(); // 扩展矩阵
        }
        verticesList.add(vertex); // 添加顶点
        vertexIndexMap.put(vertex, verticesList.size() - 1); // 更新索引映射
    }

    /**
     * 扩展邻接矩阵的容量，使其容量翻倍。
     */
    private void expandMatrix() {
        capacity *= 2; // 容量翻倍
        boolean[][] newMatrix = new boolean[capacity][capacity]; // 创建新邻接矩阵
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length); // 复制旧矩阵的数据
        }
        matrix = newMatrix; // 更新矩阵引用
    }

    /**
     * 在图中添加一条边。
     *
     * @param edge 要添加的边
     */
    @Override
    public void addEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource(); // 获取源顶点
        Vertex<T> dest = edge.getDestination(); // 获取目标顶点
        int srcIndex = vertexIndexMap.get(src); // 获取源顶点的索引
        int destIndex = vertexIndexMap.get(dest); // 获取目标顶点的索引
        matrix[srcIndex][destIndex] = true; // 在邻接矩阵中标记边的存在
    }

    /**
     * 从图中删除一个顶点，并移除所有相关的边。
     *
     * @param vertex 要删除的顶点
     */
    @Override
    public void deleteVertex(Vertex<T> vertex) {
        int index = vertexIndexMap.get(vertex); // 获取顶点的索引
        verticesList.remove(index); // 删除顶点
        vertexIndexMap.remove(vertex); // 从映射中移除顶点
        // 更新索引映射
        for (int i = index; i < verticesList.size(); ++i) {
            vertexIndexMap.put(verticesList.get(i), i);
        }
        // 删除与该顶点相关的行和列
        for (int i = 0; i < verticesList.size() + 1; ++i) {
            for (int j = index; j < verticesList.size(); ++j) {
                matrix[i][j] = matrix[i][j + 1]; // 复制边
            }
            matrix[i][verticesList.size()] = false; // 清空最后一列
        }
    }

    /**
     * 删除两顶点之间的边。
     *
     * @param edge 要删除的边
     */
    @Override
    public void deleteEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource(); // 获取源顶点
        Vertex<T> dest = edge.getDestination(); // 获取目标顶点
        int srcIndex = vertexIndexMap.get(src); // 获取源顶点的索引
        int destIndex = vertexIndexMap.get(dest); // 获取目标顶点的索引
        if (matrix[srcIndex][destIndex]) { // 如果边存在
            matrix[srcIndex][destIndex] = false; // 在邻接矩阵中标记边的不存在
        }
    }

    /**
     * 返回给定顶点的邻居列表。
     *
     * @param vertex 要查询邻居的顶点
     * @return 邻居列表
     */
    @Override
    public List<Vertex<T>> getNeighbours(Vertex<T> vertex) {
        List<Vertex<T>> neighbours = new ArrayList<>(); // 存储邻居的列表
        if (!vertexIndexMap.containsKey(vertex)) {
            return neighbours; // 如果顶点不存在，返回空列表
        }
        int index = vertexIndexMap.get(vertex); // 获取顶点的索引
        for (int j = 0; j < verticesList.size(); j++) {
            if (matrix[index][j]) { // 如果邻接矩阵中存在边
                neighbours.add(verticesList.get(j)); // 添加到邻居列表
            }
        }
        return neighbours; // 返回邻居列表
    }

    /**
     * 从文件中读取图。
     * 文件的第一行应包含顶点的数量。
     * 后续行应包含顶点标签。
     * 接下来的一行应包含边的数量，后续行应包含每条边的源和目标顶点标签。
     *
     * @param filename 要读取的文件名
     */
    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            line = br.readLine(); // 读取第一行
            if (line == null) {
                return; // 如果文件为空，直接返回
            }
            int numVertices = Integer.parseInt(line.trim()); // 读取顶点数量
            for (int i = 0; i < numVertices; ++i) {
                line = br.readLine(); // 读取每个顶点的标签
                if (line == null) {
                    break; // 如果读取行为空，退出
                }
                String[] parts = line.trim().split("\\s+", 2); // 分割顶点标签
                T label = (T) parts[1]; // 获取顶点标签
                addVertex(new Vertex<>(label)); // 添加顶点
            }
            line = br.readLine(); // 读取边的数量
            if (line == null) {
                return; // 如果读取行为空，直接返回
            }
            int numEdges = Integer.parseInt(line.trim()); // 读取边数量
            for (int i = 0; i < numEdges; ++i) {
                line = br.readLine(); // 读取每条边
                if (line == null) {
                    break; // 如果读取行为空，退出
                }
                String[] parts = line.trim().split("\\s+", 2); // 分割边的定义
                T srcLabel = (T) parts[0]; // 源顶点标签
                T destLabel = (T) parts[1]; // 目标顶点标签
                Vertex<T> src = findVertexByLabel(srcLabel); // 查找源顶点
                Vertex<T> dest = findVertexByLabel(destLabel); // 查找目标顶点
                if (src != null && dest != null) {
                    addEdge(new Edge<>(src, dest)); // 添加边
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // 处理文件读取异常
        }
    }

    /**
     * 根据标签查找顶点。
     *
     * @param label 要查找的顶点标签
     * @return 找到的顶点，如果未找到则返回 null
     */
    Vertex<T> findVertexByLabel(T label) {
        for (Vertex<T> v : verticesList) {
            if (v.label().equals(label)) {
                return v; // 返回找到的顶点
            }
        }
        return null; // 未找到返回 null
    }

    /**
     * 返回图中的所有顶点。
     *
     * @return 顶点列表
     */
    @Override
    public List<Vertex<T>> getVertices() {
        return new ArrayList<>(verticesList); // 返回顶点的副本
    }

    /**
     * 比较此图与另一个对象的相等性。
     *
     * @param o 要比较的对象
     * @return 如果两个图相等则返回 true，否则返回 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; // 同一个对象
        }
        if (o == null || getClass() != o.getClass()) {
            return false; // 类型不匹配
        }
        AdjacencyMatrixGraph<?> that = (AdjacencyMatrixGraph<?>) o; // 类型转换
        if (capacity != that.capacity) {
            return false; // 容量不同
        }
        if (!verticesList.equals(that.verticesList)) {
            return false; // 顶点列表不同
        }
        return Arrays.deepEquals(matrix, that.matrix); // 矩阵内容不同
    }

    /**
     * 返回图的哈希码值。
     *
     * @return 哈希码值
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(verticesList, vertexIndexMap, capacity); // 计算哈希码
        result = 31 * result + Arrays.deepHashCode(matrix); // 包含邻接矩阵的哈希码
        return result; // 返回最终哈希码
    }

    /**
     * 返回图的字符串表示。
     *
     * @return 图的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyMatrixGraph:\n");
        sb.append("Vertices:\n");
        for (Vertex<T> v : verticesList) {
            sb.append(v).append("\n"); // 添加顶点信息
        }
        sb.append("Edges:\n");
        for (int i = 0; i < verticesList.size(); i++) {
            for (int j = 0; j < verticesList.size(); j++) {
                if (matrix[i][j]) { // 如果存在边
                    sb.append("Edge from ").append(verticesList.get(i).label())
                            .append(" to ").append(verticesList.get(j).label()).append("\n"); // 添加边信息
                }
            }
        }
        return sb.toString(); // 返回字符串表示
    }

    /**
     * 返回图中所有边的集合。
     *
     * @return 边的集合
     */
    public Collection<Edge<T>> getEdges() {
        List<Edge<T>> edges = new ArrayList<>(); // 存储边的列表
        for (int i = 0; i < verticesList.size(); i++) {
            for (int j = 0; j < verticesList.size(); j++) {
                if (matrix[i][j]) { // 如果存在边
                    edges.add(new Edge<>(verticesList.get(i), verticesList.get(j))); // 添加边
                }
            }
        }
        return edges; // 返回边的集合
    }
}
