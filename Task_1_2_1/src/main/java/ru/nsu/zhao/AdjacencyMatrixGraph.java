package ru.nsu.zhao;
import java.util.*;

public class AdjacencyMatrixGraph implements Graph {
    private final Map<String, Integer> vertexIndexMap; // 顶点与索引的映射
    private final List<String> vertices; // 顶点列表
    private int[][] adjacencyMatrix; // 邻接矩阵

    public AdjacencyMatrixGraph() {
        this.vertexIndexMap = new HashMap<>();
        this.vertices = new ArrayList<>();
        this.adjacencyMatrix = new int[0][0];
    }

    // 添加顶点
    @Override
    public void addVertex(String vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            vertices.add(vertex);
            vertexIndexMap.put(vertex, vertices.size() - 1);
            expandMatrix();
        }
    }

    // 删除顶点
    @Override
    public void removeVertex(String vertex) {
        if (vertexIndexMap.containsKey(vertex)) {
            int index = vertexIndexMap.get(vertex);
            vertices.remove(vertex);
            vertexIndexMap.remove(vertex);
            shrinkMatrix(index);
        }
    }

    // 添加边
    @Override
    public void addEdge(String vertex1, String vertex2) {
        if (vertexIndexMap.containsKey(vertex1) && vertexIndexMap.containsKey(vertex2)) {
            int i = vertexIndexMap.get(vertex1);
            int j = vertexIndexMap.get(vertex2);
            adjacencyMatrix[i][j] = 1;
        }
    }

    // 删除边
    @Override
    public void removeEdge(String vertex1, String vertex2) {
        if (vertexIndexMap.containsKey(vertex1) && vertexIndexMap.containsKey(vertex2)) {
            int i = vertexIndexMap.get(vertex1);
            int j = vertexIndexMap.get(vertex2);
            adjacencyMatrix[i][j] = 0;
        }
    }

    // 获取邻居
    @Override
    public List<String> getNeighbors(String vertex) {
        List<String> neighbors = new ArrayList<>();
        if (vertexIndexMap.containsKey(vertex)) {
            int index = vertexIndexMap.get(vertex);
            for (int j = 0; j < vertices.size(); j++) {
                if (adjacencyMatrix[index][j] == 1) {
                    neighbors.add(vertices.get(j));
                }
            }
        }
        return neighbors;
    }

    // 从文件中读取图
    @Override
    public void readFromFile(String filePath) {
        // 实现从文件读取图的逻辑
    }

    // 拓扑排序
    @Override
    public List<String> topologicalSort() {
        List<String> sorted = new ArrayList<>();
        Map<String, Integer> inDegree = new HashMap<>();
        for (String v : vertices) {
            inDegree.put(v, 0);
        }

        // 计算所有顶点的入度
        for (String v : vertices) {
            for (String neighbor : getNeighbors(v)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<String> queue = new LinkedList<>();
        for (String v : inDegree.keySet()) {
            if (inDegree.get(v) == 0) {
                queue.add(v);
            }
        }

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            sorted.add(vertex);
            for (String neighbor : getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sorted.size() != vertices.size()) {
            throw new RuntimeException("图中存在环！");
        }

        return sorted;
    }

    // 输出图的字符串表示
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency Matrix Graph:\n");
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(vertices.get(i)).append(": ");
            for (int j = 0; j < vertices.size(); j++) {
                sb.append(adjacencyMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // 检查两个图是否相等
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyMatrixGraph that = (AdjacencyMatrixGraph) obj;
        return Arrays.deepEquals(adjacencyMatrix, that.adjacencyMatrix) && Objects.equals(vertices, that.vertices);
    }

    // 扩展邻接矩阵
    private void expandMatrix() {
        int newSize = vertices.size();
        int[][] newMatrix = new int[newSize][newSize];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, adjacencyMatrix[i].length);
        }
        adjacencyMatrix = newMatrix;
    }

    // 缩小邻接矩阵
    private void shrinkMatrix(int removeIndex) {
        int newSize = vertices.size();
        int[][] newMatrix = new int[newSize][newSize];
        for (int i = 0, ni = 0; i < adjacencyMatrix.length; i++) {
            if (i == removeIndex) continue;
            for (int j = 0, nj = 0; j < adjacencyMatrix[i].length; j++) {
                if (j == removeIndex) continue;
                newMatrix[ni][nj] = adjacencyMatrix[i][j];
                nj++;
            }
            ni++;
        }
        adjacencyMatrix = newMatrix;
    }
}
