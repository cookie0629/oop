package ru.nsu.zhao;
import java.util.List;

public interface Graph {
    // 添加顶点
    void addVertex(String vertex);

    // 删除顶点
    void removeVertex(String vertex);

    // 添加边缘
    void addEdge(String vertex1, String vertex2);

    // 删除边缘
    void removeEdge(String vertex1, String vertex2);

    // 获取某个顶点的所有邻居
    List<String> getNeighbors(String vertex);

    // 从文件中读取图
    void readFromFile(String filePath);

    // 输出图的字符串表示
    String toString();

    // 拓扑排序
    List<String> topologicalSort();
}

