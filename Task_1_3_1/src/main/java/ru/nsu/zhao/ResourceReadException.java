package ru.nsu.zhao;

/**
 * 自定义异常类，用于处理资源读取错误。
 */
public class ResourceReadException extends Exception {
    /**
     * 构造一个新的 ResourceReadException 异常，包含指定的错误信息和原因。
     *
     * @param message 错误信息
     * @param cause   异常原因
     */
    public ResourceReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
