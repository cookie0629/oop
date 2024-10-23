package ru.nsu.zhao;

/**
 * 异常类，当在图中检测到环时（如在拓扑排序过程中），抛出该异常。
 */
public class GraphCycleException extends Exception {

    /**
     * GraphCycleException 构造方法。
     * @param message 当异常发生时传递的错误信息。
     */
    public GraphCycleException(String message) {
        super(message);
    }
}
