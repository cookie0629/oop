package ru.nsu.zhao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph adjacencyMatrixGraph;
    private Graph incidenceMatrixGraph;
    private Graph adjacencyListGraph;

    @BeforeEach
    void setUp() {
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(5);
        incidenceMatrixGraph = new IncidenceMatrixGraph(5, 5);
        adjacencyListGraph = new AdjacencyListGraph();
    }

    @Test
    void testAddEdgeAndNeighbors_AdjacencyMatrix() {
        adjacencyMatrixGraph.addEdge(0, 1);
        List<Integer> neighbors = adjacencyMatrixGraph.getNeighbors(0);
        assertEquals(List.of(1), neighbors);
    }

    @Test
    void testAddEdgeAndNeighbors_IncidenceMatrix() {
        incidenceMatrixGraph.addEdge(0, 1);
        List<Integer> neighbors = incidenceMatrixGraph.getNeighbors(0);
        assertEquals(List.of(1), neighbors);
    }

    @Test
    void testAddEdgeAndNeighbors_AdjacencyList() {
        adjacencyListGraph.addVertex(0);
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addEdge(0, 1);
        List<Integer> neighbors = adjacencyListGraph.getNeighbors(0);
        assertEquals(List.of(1), neighbors);
    }

    @Test
    void testRemoveEdge_AdjacencyMatrix() {
        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.removeEdge(0, 1);
        List<Integer> neighbors = adjacencyMatrixGraph.getNeighbors(0);
        assertEquals(List.of(), neighbors);
    }

    @Test
    void testRemoveEdge_IncidenceMatrix() {
        incidenceMatrixGraph.addEdge(0, 1);
        incidenceMatrixGraph.removeEdge(0, 1);
        List<Integer> neighbors = incidenceMatrixGraph.getNeighbors(0);
        assertEquals(List.of(), neighbors);
    }

    @Test
    void testRemoveEdge_AdjacencyList() {
        adjacencyListGraph.addVertex(0);
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.removeEdge(0, 1);
        List<Integer> neighbors = adjacencyListGraph.getNeighbors(0);
        assertEquals(List.of(), neighbors);
    }

    @Test
    void testTopologicalSort_AdjacencyMatrix() {
        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.addEdge(1, 2);
        List<Integer> topoOrder = adjacencyMatrixGraph.topologicalSort();
        assertEquals(List.of(0, 1, 2), topoOrder);
    }

    @Test
    void testTopologicalSort_AdjacencyList() {
        adjacencyListGraph.addVertex(0);
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.addEdge(1, 2);
        List<Integer> topoOrder = adjacencyListGraph.topologicalSort();
        assertEquals(List.of(0, 1, 2), topoOrder);
    }

    @Test
    void testTopologicalSort_IncidenceMatrix() {
        incidenceMatrixGraph.addEdge(0, 1);
        incidenceMatrixGraph.addEdge(1, 2);
        List<Integer> topoOrder = incidenceMatrixGraph.topologicalSort();
        assertNotNull(topoOrder);
    }
}
