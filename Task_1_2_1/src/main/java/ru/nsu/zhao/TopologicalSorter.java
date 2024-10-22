package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 拓扑排序类，用于对有向无环图（DAG）进行拓扑排序。
 *
 * @param <T> 顶点存储的元素类型
 */
public class TopologicalSorter<T> {

    /**
     * 执行拓扑排序。
     *
     * @param graph 要进行拓扑排序的图
     * @return 排序后的顶点列表
     */
    public static <T> List<Vertex<T>> topologicalSort(Graph<T> graph) {
        List<Vertex<T>> sorted = new ArrayList<>(); // 存储排序后的顶点
        Map<Vertex<T>, Integer> inDegree = new HashMap<>(); // 存储每个顶点的入度

        // 初始化每个顶点的入度为0
        for (Vertex<T> vertex : graph.getVertices()) {
            inDegree.put(vertex, 0);
        }

        // 计算每个顶点的入度
        for (Vertex<T> vertex : graph.getVertices()) {
            for (Vertex<T> neighbour : graph.getNeighbours(vertex)) {
                inDegree.put(neighbour, inDegree.get(neighbour) + 1);
            }
        }

        Queue<Vertex<T>> queue = new LinkedList<>(); // 存储入度为0的顶点
        // 将入度为0的顶点加入队列
        for (Map.Entry<Vertex<T>, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        // 进行拓扑排序
        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll(); // 从队列中取出一个入度为0的顶点
            sorted.add(vertex); // 将其添加到排序结果中

            // 减少相邻顶点的入度
            for (Vertex<T> neighbour : graph.getNeighbours(vertex)) {
                inDegree.put(neighbour, inDegree.get(neighbour) - 1);
                // 如果相邻顶点的入度变为0，则加入队列
                if (inDegree.get(neighbour) == 0) {
                    queue.offer(neighbour);
                }
            }
        }

        return sorted; // 返回拓扑排序的结果
    }
}
