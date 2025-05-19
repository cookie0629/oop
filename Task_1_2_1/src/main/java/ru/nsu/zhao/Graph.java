package ru.nsu.zhao;

import java.util.List;

public interface Graph {
    /**
     * 在两个顶点之间添加一条有向边。
     * @param source      源顶点
     *
     * @param destination 目标顶点
     */
    void addEdge(int source, int destination);

    /**
     * 删除两个顶点之间的有向边。
     * @param source      源顶点
     *
     * @param destination 目标顶点
     */
    void removeEdge(int source, int destination);

    /**
     * 返回指定顶点的相邻顶点列表。
     * @param vertex 要获取相邻顶点的顶点
     *
     * @return 相邻顶点的列表
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * 从文件中读取图数据。文件格式：边以 "源顶点,目标顶点" 的形式表示。
     * @param filePath 文件路径
     *
     * @throws Exception 如果读取文件时发生错误
     */
    void readFromFile(String filePath) throws Exception;

    /**
     * 返回图的字符串表示形式。
     * @return 图的字符串表示
     *
     */
    String toString();

    /**
     * 比较图是否相等。
     * @param o 要比较的对象
     *
     * @return 如果图相等返回 true，否则返回 false
     *
     */
    boolean equals(Object o);

    /**
     * 对图进行拓扑排序。
     * @return 按拓扑顺序排列的顶点列表
     *
     * @throws GraphCycleException 如果图中存在环，则无法进行排序
     */
    List<Integer> topologicalSort() throws GraphCycleException;
}

