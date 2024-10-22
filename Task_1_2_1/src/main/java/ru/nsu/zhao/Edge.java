package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示图中的一条边，连接源顶点和目标顶点。
 *
 * @param <T> 存储在图的顶点中的元素类型
 */
public class Edge<T> {
    private final Vertex<T> source;      // 源顶点
    private final Vertex<T> destination; // 目标顶点

    /**
     * 构造一个具有指定源和目标顶点的新边。
     *
     * @param source      边的源顶点
     * @param destination 边的目标顶点
     */
    public Edge(Vertex<T> source, Vertex<T> destination) {
        this.source = source;          // 初始化源顶点
        this.destination = destination; // 初始化目标顶点
    }

    /**
     * 返回边的源顶点。
     *
     * @return 源顶点
     */
    public Vertex<T> getSource() {
        return source; // 返回源顶点
    }

    /**
     * 返回边的目标顶点。
     *
     * @return 目标顶点
     */
    public Vertex<T> getDestination() {
        return destination; // 返回目标顶点
    }

    /**
     * 比较此边与另一个对象的相等性。
     *
     * @param o 用于比较的对象
     * @return 如果边相等则返回true，否则返回false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; // 如果是同一个对象，返回true
        }
        if (o == null || getClass() != o.getClass()) {
            return false; // 如果对象为null或类型不同，返回false
        }
        Edge<?> edge = (Edge<?>) o; // 强制转换为Edge类型
        // 比较源顶点和目标顶点是否相等
        return Objects.equals(source, edge.source)
                && Objects.equals(destination, edge.destination);
    }

    /**
     * 返回边的哈希码值。
     *
     * @return 哈希码值
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, destination); // 计算源和目标顶点的哈希码
    }

    /**
     * 返回边的字符串表示形式。
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        return "Edge { " + "from " + source.getLabel() + ", to " + destination.getLabel() + " }"; // 返回边的描述
    }
}
