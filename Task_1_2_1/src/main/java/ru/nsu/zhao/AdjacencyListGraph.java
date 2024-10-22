package ru.nsu.zhao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 使用邻接表实现图的类。
 *
 * @param <T> 存储在图的顶点中的元素类型
 */
public class AdjacencyListGraph<T> implements Graph<T> {
    private final Map<Vertex<T>, List<Vertex<T>>> adjList; // 存储邻接表
    private final Set<Vertex<T>> vertices; // 存储图中的顶点

    /**
     * 构造一个新的空邻接表图。
     */
    public AdjacencyListGraph() {
        adjList = new HashMap<>(); // 初始化邻接表
        vertices = new HashSet<>(); // 初始化顶点集合
    }

    /**
     * 向图中添加一个顶点。
     *
     * @param vertex 要添加的顶点
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex); // 将顶点添加到集合中
        adjList.put(vertex, new ArrayList<>()); // 在邻接表中初始化该顶点的邻接列表
    }

    /**
     * 在两个顶点之间添加一条边。
     *
     * @param edge 要添加的边
     */
    @Override
    public void addEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource(); // 获取边的源顶点
        Vertex<T> dest = edge.getDestination(); // 获取边的目标顶点
        adjList.get(src).add(dest); // 在邻接表中添加目标顶点到源顶点的邻接列表
    }

    /**
     * 从图中删除一个顶点。
     *
     * @param vertex 要删除的顶点
     */
    @Override
    public void deleteVertex(Vertex<T> vertex) {
        vertices.remove(vertex); // 从顶点集合中删除顶点
        adjList.remove(vertex); // 从邻接表中删除顶点的邻接列表
        for (List<Vertex<T>> neighbours : adjList.values()) {
            neighbours.remove(vertex); // 从所有邻接列表中删除该顶点
        }
    }

    /**
     * 删除两个顶点之间的边。
     *
     * @param edge 要删除的边
     */
    @Override
    public void deleteEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource(); // 获取源顶点
        Vertex<T> dest = edge.getDestination(); // 获取目标顶点
        adjList.get(src).remove(dest); // 从源顶点的邻接列表中删除目标顶点
    }

    /**
     * 返回给定顶点的邻居列表。
     *
     * @param vertex 要返回邻居的顶点
     * @return 邻居列表
     */
    @Override
    public List<Vertex<T>> getNeighbours(Vertex<T> vertex) {
        if (!adjList.containsKey(vertex)) {
            return new ArrayList<>(); // 如果顶点不在邻接表中，返回空列表
        }
        return new ArrayList<>(adjList.get(vertex)); // 返回顶点的邻接列表
    }

    /**
     * 从文件中读取图。
     * 文件的第一行应包含顶点的数量。
     * 随后的每一行应包含一个顶点的标签。
     * 在顶点之后，下一行应包含边的数量，
     * 后续行应包含每条边的源和目标顶点标签。
     *
     * @param filename 要读取的文件名
     * @throws GraphFileReadException 如果读取文件时出现错误
     */
    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            line = br.readLine(); // 读取第一行
            int numVertices = Integer.parseInt(line.trim()); // 解析顶点数量
            for (int i = 0; i < numVertices; ++i) {
                line = br.readLine(); // 读取每个顶点的标签
                if (line == null) {
                    break; // 如果没有更多行，退出
                }
                String[] parts = line.trim().split("\\s+", 2); // 按空格分割
                T label = (T) parts[1]; // 获取标签
                addVertex(new Vertex<>(label)); // 添加顶点
            }
            line = br.readLine(); // 读取边的数量
            int numEdges = Integer.parseInt(line.trim()); // 解析边的数量
            for (int i = 0; i < numEdges; ++i) {
                line = br.readLine(); // 读取每条边的源和目标标签
                if (line == null) {
                    break; // 如果没有更多行，退出
                }
                String[] parts = line.trim().split("\\s+", 2); // 按空格分割
                T srcLabel = (T) parts[0]; // 获取源标签
                T destLabel = (T) parts[1]; // 获取目标标签
                Vertex<T> src = findVertexByLabel(srcLabel); // 查找源顶点
                Vertex<T> dest = findVertexByLabel(destLabel); // 查找目标顶点
                if (src != null && dest != null) {
                    addEdge(new Edge<>(src, dest)); // 添加边
                }
            }
        } catch (IOException e) {
            throw new GraphFileReadException("从文件读取图失败: " + filename, e); // 抛出自定义异常
        }
    }

    /**
     * 根据标签查找顶点。
     *
     * @param label 要查找的顶点标签
     * @return 如果找到，返回顶点，否则返回null
     */
    Vertex<T> findVertexByLabel(T label) {
        for (Vertex<T> v : vertices) {
            if (v.getLabel().equals(label)) {
                return v; // 找到顶点，返回
            }
        }
        return null; // 未找到，返回null
    }

    /**
     * 返回图中的所有顶点列表。
     *
     * @return 顶点列表
     */
    @Override
    public List<Vertex<T>> getVertices() {
        return new ArrayList<>(vertices); // 返回顶点集合的列表
    }

    /**
     * 比较该图与另一个对象的相等性。
     *
     * @param o 要比较的对象
     * @return 如果图相等，返回true；否则返回false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; // 如果是同一对象，返回true
        }
        if (o == null || getClass() != o.getClass()) {
            return false; // 如果对象为null或类型不同，返回false
        }
        AdjacencyListGraph<?> that = (AdjacencyListGraph<?>) o; // 强制转换为AdjacencyListGraph类型
        if (!adjList.equals(that.adjList)) {
            return false; // 比较邻接表是否相等
        }
        return vertices.equals(that.vertices); // 比较顶点集合是否相等
    }

    /**
     * 返回该图的哈希码值。
     *
     * @return 哈希码值
     */
    @Override
    public int hashCode() {
        return Objects.hash(adjList, vertices); // 计算哈希码
    }

    /**
     * 返回图的字符串表示。
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // 创建字符串构建器
        sb.append("AdjacencyListGraph:\n");
        sb.append("Vertices:\n");
        for (Vertex<T> v : vertices) {
            sb.append(v).append("\n"); // 添加每个顶点的字符串表示
        }
        sb.append("Adjacency List:\n");
        for (Map.Entry<Vertex<T>, List<Vertex<T>>> entry : adjList.entrySet()) {
            sb.append(entry.getKey().getLabel()).append(": "); // 添加顶点标签
            for (Vertex<T> neighbor : entry.getValue()) {
                sb.append(neighbor.getLabel()).append(" "); // 添加邻居的标签
            }
            sb.append("\n");
        }
        return sb.toString(); // 返回图的字符串表示
    }
}
