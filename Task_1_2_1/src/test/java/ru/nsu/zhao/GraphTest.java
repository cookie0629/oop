package ru.nsu.zhao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph adjacencyMatrixGraph;
    private Graph adjacencyListGraph;

    @BeforeEach
    void setUp() {
        adjacencyMatrixGraph = new AdjacencyMatrixGraph(5);
        adjacencyListGraph = new AdjacencyListGraph();
        for (int i = 0; i < 5; i++) {
            adjacencyListGraph.addVertex(i);
        }
    }

    @Test
    void testAddVertex() {
        // Adjacency List Graph
        adjacencyListGraph.addVertex(5);
        assertEquals(List.of(), adjacencyListGraph.getNeighbors(5));

        // Adjacency Matrix Graph
        assertDoesNotThrow(() -> adjacencyMatrixGraph.addVertex(4));  // Already exists
    }

    @Test
    void testAddEdge() {
        // Adjacency List Graph
        adjacencyListGraph.addEdge(0, 1);
        assertEquals(List.of(1), adjacencyListGraph.getNeighbors(0));

        // Adjacency Matrix Graph
        adjacencyMatrixGraph.addEdge(0, 1);
        assertEquals(List.of(1), adjacencyMatrixGraph.getNeighbors(0));
    }

    @Test
    void testRemoveEdge() {
        // Adjacency List Graph
        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.removeEdge(0, 1);
        assertEquals(List.of(), adjacencyListGraph.getNeighbors(0));

        // Adjacency Matrix Graph
        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.removeEdge(0, 1);
        assertEquals(List.of(), adjacencyMatrixGraph.getNeighbors(0));
    }

    @Test
    void testRemoveVertex() {
        // Adjacency List Graph
        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.removeVertex(1);
        assertEquals(List.of(), adjacencyListGraph.getNeighbors(0));

        // Adjacency Matrix Graph
        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.removeVertex(1);
        assertEquals(List.of(), adjacencyMatrixGraph.getNeighbors(0));
    }

    @Test
    void testGetNeighbors() {
        // Adjacency List Graph
        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.addEdge(0, 2);
        assertEquals(List.of(1, 2), adjacencyListGraph.getNeighbors(0));

        // Adjacency Matrix Graph
        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.addEdge(0, 2);
        assertEquals(List.of(1, 2), adjacencyMatrixGraph.getNeighbors(0));
    }

    @Test
    void testToString() {
        // Adjacency List Graph
        adjacencyListGraph.addEdge(0, 1);
        String expectedAdjacencyList = "0: [1]\n1: []\n2: []\n3: []\n4: []\n";
        assertEquals(expectedAdjacencyList, adjacencyListGraph.toString());

        // Adjacency Matrix Graph
        adjacencyMatrixGraph.addEdge(0, 1);
        String expectedAdjacencyMatrix = "0 1 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n";
        assertEquals(expectedAdjacencyMatrix, adjacencyMatrixGraph.toString());
    }

    @Test
    void testEquals() {
        // Create another graph to compare
        Graph otherAdjacencyListGraph = new AdjacencyListGraph();
        for (int i = 0; i < 5; i++) {
            otherAdjacencyListGraph.addVertex(i);
        }
        otherAdjacencyListGraph.addEdge(0, 1);

        // Adjacency List Graph
        adjacencyListGraph.addEdge(0, 1);
        assertEquals(otherAdjacencyListGraph, adjacencyListGraph);

        // Adjacency Matrix Graph
        Graph otherAdjacencyMatrixGraph = new AdjacencyMatrixGraph(5);
        otherAdjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.addEdge(0, 1);
        assertEquals(otherAdjacencyMatrixGraph, adjacencyMatrixGraph);
    }
}
