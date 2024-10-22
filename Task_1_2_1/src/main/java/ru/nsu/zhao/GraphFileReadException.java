package ru.nsu.zhao;

/**
 * 自定义异常类，用于处理图文件读取时的错误。
 */
public class GraphFileReadException extends RuntimeException {
    /**
     * 构造函数，创建一个新的 GraphFileReadException 实例。
     *
     * @param message 错误信息，描述异常的具体情况
     * @param cause   引发此异常的原始原因（Throwable对象）
     */
    public GraphFileReadException(String message, Throwable cause) {
        super(message, cause); // 调用父类的构造函数，传递错误信息和原始原因
    }
}
