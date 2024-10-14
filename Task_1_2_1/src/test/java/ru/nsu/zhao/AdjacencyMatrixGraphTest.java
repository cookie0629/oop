package ru.nsu.zhao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class AdjacencyMatrixGraphTest {

    private AdjacencyMatrixGraph graph;

    // 每个测试前初始化一个空的图
    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph();
    }

    // 测试添加顶点
    @Test
    void testAddVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        assertEquals(2, graph.getNeighbors("A").size()); // 顶点A没有邻居，所以邻居数量应该是0
    }

    // 测试添加边
    @Test
    void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        List<String> neighbors = graph.getNeighbors("A");
        assertTrue(neighbors.contains("B"));
        assertFalse(neighbors.contains("A")); // A不应该是自己的邻居
    }

    // 测试删除边
    @Test
    void testRemoveEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeEdge("A", "B");
        List<String> neighbors = graph.getNeighbors("A");
        assertFalse(neighbors.contains("B")); // 删除边后B不再是A的邻居
    }

    // 测试删除顶点
    @Test
    void testRemoveVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        graph.removeVertex("A");
        assertNull(graph.getNeighbors("A")); // 顶点A被删除后，应该返回null
    }

    // 测试拓扑排序
    @Test
    void testTopologicalSort() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        List<String> sorted = graph.topologicalSort();
        assertEquals(List.of("A", "B", "C"), sorted); // 拓扑排序结果应该是A->B->C
    }

    // 测试图的字符串表示
    @Test
    void testToString() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        String expected = "Adjacency Matrix Graph:\nA: 0 1 \nB: 0 0 \n";
        assertEquals(expected, graph.toString());
    }
}

