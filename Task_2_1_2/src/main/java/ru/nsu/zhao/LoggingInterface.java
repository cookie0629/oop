package ru.nsu.zhao;

/**
 * Interface for logging messages
 * 日志记录接口
 */
public interface LoggingInterface {
    void logInfo(String message);    // For information messages / 用于信息消息
    void logError(String message);  // For error messages / 用于错误消息
}
