package ru.nsu.zhao;

import java.util.List;

/**
 * 图的接口，定义图的基本操作。
 *
 * @param <T> 顶点元素的类型
 */
public interface Graph<T> {
    void addVertex(Vertex<T> vertex);
    void addEdge(Edge<T> edge);

    void deleteVertex(Vertex<T> vertex);

    void deleteEdge(Edge<T> edge);

    List<Vertex<T>> getNeighbours(Vertex<T> vertex);

    void readFromFile(String filename);

    List<Vertex<T>> getVertices();
}