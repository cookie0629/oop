package ru.nsu.zhao;

public class Main {

    public static void main(String[] args) {
        // 初始化带有4个顶点的图
        Graph graph = new IncidenceMatrixGraph(4);

        // 添加几条边
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        // 打印图的初始状态
        System.out.println("Graph before reading from file:");
        System.out.println(graph);

        // 从文件中读取图的数据
        try {
            graph.readFromFile("graph_input.txt");
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // 打印图的最终状态
        System.out.println("Graph after reading from file:");
        System.out.println(graph);

        // 尝试进行拓扑排序
        try {
            System.out.println("Topological Sort:");
            System.out.println(graph.topologicalSort());
        } catch (GraphCycleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
