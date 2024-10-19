package ru.nsu.zhao;

import java.io.IOException;
import java.util.List;

public interface Graph {
    // 添加顶点
    void addVertex(String vertex);

    // 删除顶点
    void removeVertex(String vertex);

    // 添加边
    void addEdge(String vertex1, String vertex2);

    // 删除边
    void removeEdge(String vertex1, String vertex2);

    // 获取邻居列表
    List<String> getNeighbors(String vertex);

    // 从文件加载图
    void loadFromFile(String filename) throws IOException;

    // 拓扑排序
    List<String> topologicalSort();

    // 字符串输出
    String toString();

    // 相等比较
    boolean equals(Object obj);
}
