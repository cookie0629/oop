package ru.nsu.zhao;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph adjacencyListGraph;
    private Graph adjacencyMatrixGraph;
    private Graph incidenceMatrixGraph;

    @BeforeEach
    void setUp() {
        adjacencyListGraph = new AdjacencyListGraph(4);
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(4);
        incidenceMatrixGraph = new IncidenceMatrixGraph(4);
    }

    @Test
    void testAddAndRemoveEdge() {
        adjacencyListGraph.addEdge(0, 1);
        adjacencyMatrixGraph.addEdge(0, 1);
        incidenceMatrixGraph.addEdge(0, 1);

        // 检查是否正确添加边
        assertTrue(adjacencyListGraph.getNeighbors(0).contains(1));
        assertTrue(adjacencyMatrixGraph.getNeighbors(0).contains(1));
        assertTrue(incidenceMatrixGraph.getNeighbors(0).contains(1));

        // 移除边
        adjacencyListGraph.removeEdge(0, 1);
        adjacencyMatrixGraph.removeEdge(0, 1);
        incidenceMatrixGraph.removeEdge(0, 1);

        // 检查是否正确移除边
        assertFalse(adjacencyListGraph.getNeighbors(0).contains(1));
        assertFalse(adjacencyMatrixGraph.getNeighbors(0).contains(1));
        assertFalse(incidenceMatrixGraph.getNeighbors(0).contains(1));
    }

    @Test
    void testTopologicalSort() throws GraphCycleException {
        // 为每个图添加边，形成一个无环图
        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.addEdge(1, 2);
        adjacencyListGraph.addEdge(2, 3);

        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.addEdge(1, 2);
        adjacencyMatrixGraph.addEdge(2, 3);

        incidenceMatrixGraph.addEdge(0, 1);
        incidenceMatrixGraph.addEdge(1, 2);
        incidenceMatrixGraph.addEdge(2, 3);

        // 检查拓扑排序的正确性
        List<Integer> expectedOrder = List.of(0, 1, 2, 3);
        assertEquals(expectedOrder, adjacencyListGraph.topologicalSort());
        assertEquals(expectedOrder, adjacencyMatrixGraph.topologicalSort());
        assertEquals(expectedOrder, incidenceMatrixGraph.topologicalSort());
    }

    @Test
    void testCycleDetection() {
        // 为每个图添加带有环的边
        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.addEdge(1, 2);
        adjacencyListGraph.addEdge(2, 0); // 环

        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.addEdge(1, 2);
        adjacencyMatrixGraph.addEdge(2, 0); // 环

        incidenceMatrixGraph.addEdge(0, 1);
        incidenceMatrixGraph.addEdge(1, 2);
        incidenceMatrixGraph.addEdge(2, 0); // 环

        // 检查是否抛出 GraphCycleException 异常
        assertThrows(GraphCycleException.class, () -> adjacencyListGraph.topologicalSort());
        assertThrows(GraphCycleException.class, () -> adjacencyMatrixGraph.topologicalSort());
        assertThrows(GraphCycleException.class, () -> incidenceMatrixGraph.topologicalSort());
    }
}
