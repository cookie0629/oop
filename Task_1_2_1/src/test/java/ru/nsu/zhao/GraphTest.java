package ru.nsu.zhao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph adjacencyMatrixGraph;
    private Graph incidenceMatrixGraph;

    @BeforeEach
    void setUp() {
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(5);
        incidenceMatrixGraph = new IncidenceMatrixGraph(5, 5);
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
}
