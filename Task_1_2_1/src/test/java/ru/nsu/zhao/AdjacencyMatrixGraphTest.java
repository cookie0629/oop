package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {
    private Graph adjacencyMatrixGraph;
    private Graph adjacencyListGraph;

    public void setUp() {
        // 初始化两个不同的图实现
        adjacencyMatrixGraph = new AdjacencyMatrixGraph();
        adjacencyListGraph = new AdjacencyListGraph();

        // 添加初始顶点和边
        adjacencyMatrixGraph.addVertex("A");
        adjacencyMatrixGraph.addVertex("B");
        adjacencyMatrixGraph.addVertex("C");
        adjacencyMatrixGraph.addEdge("A", "B");
        adjacencyMatrixGraph.addEdge("B", "C");

        adjacencyListGraph.addVertex("A");
        adjacencyListGraph.addVertex("B");
        adjacencyListGraph.addVertex("C");
        adjacencyListGraph.addEdge("A", "B");
        adjacencyListGraph.addEdge("B", "C");
    }
    // 测试添加顶点
    @Test
    public void testAddVertex() {
        adjacencyMatrixGraph.addVertex("D");
        assertTrue(adjacencyMatrixGraph.getNeighbors("D").isEmpty());

        adjacencyListGraph.addVertex("D");
        assertTrue(adjacencyListGraph.getNeighbors("D").isEmpty());
    }

    // 测试删除顶点
    @Test
    public void testRemoveVertex() {
        adjacencyMatrixGraph.removeVertex("B");
        assertTrue(adjacencyMatrixGraph.getNeighbors("A").isEmpty());

        adjacencyListGraph.removeVertex("B");
        assertTrue(adjacencyListGraph.getNeighbors("A").isEmpty());
    }

    // 测试添加边
    @Test
    public void testAddEdge() {
        adjacencyMatrixGraph.addEdge("A", "C");
        assertTrue(adjacencyMatrixGraph.getNeighbors("A").contains("C"));

        adjacencyListGraph.addEdge("A", "C");
        assertTrue(adjacencyListGraph.getNeighbors("A").contains("C"));
    }

    // 测试删除边
    @Test
    public void testRemoveEdge() {
        adjacencyMatrixGraph.removeEdge("A", "B");
        assertFalse(adjacencyMatrixGraph.getNeighbors("A").contains("B"));

        adjacencyListGraph.removeEdge("A", "B");
        assertFalse(adjacencyListGraph.getNeighbors("A").contains("B"));
    }
}

