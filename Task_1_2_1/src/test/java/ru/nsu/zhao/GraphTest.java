package ru.nsu.zhao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
    }

    @Test
    void testAddVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        assertEquals(0, graph.getNeighbors("A").size(), "A should not have any neighbors");
        assertEquals(0, graph.getNeighbors("B").size(), "B should not have any neighbors");
    }

    @Test
    void testAddEdge() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        List<String> neighborsA = graph.getNeighbors("A");
        List<String> neighborsB = graph.getNeighbors("B");

        assertEquals(2, neighborsA.size(), "A should have two neighbors");
        assertTrue(neighborsA.contains("B"), "A should be connected to B");
        assertTrue(neighborsA.contains("C"), "A should be connected to C");

        assertEquals(1, neighborsB.size(), "B should have one neighbor");
        assertTrue(neighborsB.contains("A"), "B should be connected to A");
    }

    @Test
    void testRemoveVertex() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.removeVertex("C");

        List<String> neighborsA = graph.getNeighbors("A");

        assertEquals(1, neighborsA.size(), "After removing C, A should only have one neighbor");
        assertTrue(neighborsA.contains("B"), "A should still be connected to B");
    }

    @Test
    void testTopologicalSort() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");
        graph.addEdge("D", "A");

        List<String> topoSort = graph.topologicalSort();
        assertEquals(List.of("D", "A", "B", "C"), topoSort, "Topological sorting order should be [D, A, B, C]");
    }

    @Test
    void testTopologicalSortWithCycle() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A"); // This creates a cycle

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            graph.topologicalSort();
        });
        assertEquals("Graph has a cycle!", exception.getMessage());
    }

    @Test
    void testGraphEquality() {
        Graph graph2 = new Graph();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph2.addEdge("A", "B");
        graph2.addEdge("B", "C");

        assertEquals(graph, graph2, "Both graphs should be equal");

        graph2.addEdge("C", "D");
        assertNotEquals(graph, graph2, "Graphs should not be equal after adding a new edge");
    }

    @Test
    void testToString() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        String graphString = graph.toString();

        assertTrue(graphString.contains("A -> [B]"), "String representation should include edge A-B");
        assertTrue(graphString.contains("B -> [A, C]"), "String representation should include edges B-A and B-C");
        assertTrue(graphString.contains("C -> [B]"), "String representation should include edge C-B");
    }
}
