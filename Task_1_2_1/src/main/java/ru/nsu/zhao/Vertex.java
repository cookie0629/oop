package ru.nsu.zhao;

import java.util.Objects;

/**
 * 表示图中的一个顶点，具有类型为T的标签。
 *
 * @param <T> 与顶点关联的标签的类型
 */
public class Vertex<T> {
    private final T label; // 顶点的标签

    /**
     * 构造一个具有指定标签的新顶点。
     *
     * @param label 顶点的标签
     */
    public Vertex(T label) {
        this.label = label; // 初始化标签
    }

    /**
     * 返回此顶点的标签。
     *
     * @return 顶点的标签
     */
    public T getLabel() {
        return label; // 返回标签
    }

    /**
     * 指示某个其他对象是否“等于”这个对象。
     *
     * @param o 用于比较的引用对象
     * @return 如果这个对象与参数o相同，则返回true；否则返回false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; // 如果是同一个对象，返回true
        }
        if (o == null || getClass() != o.getClass()) {
            return false; // 如果对象为null或类型不同，返回false
        }

        Vertex<?> vertex = (Vertex<?>) o; // 强制转换为Vertex类型
        // 比较标签是否相等
        return Objects.equals(label, vertex.label);
    }

    /**
     * 返回该对象的哈希码值。
     *
     * @return 哈希码值
     */
    @Override
    public int hashCode() {
        return Objects.hash(label); // 计算标签的哈希码
    }

    /**
     * 返回顶点的字符串表示形式。
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        return "Vertex { " + label + " }"; // 返回顶点的描述
    }
}
