package ru.nsu.zhao;

/**
 * Console output logger implementation
 * 控制台日志记录器实现
 */
public class TerminalLogger implements LoggingInterface {
    private final String identifier;

    public TerminalLogger(String id) {
        this.identifier = id;
    }

    @Override
    public void logInfo(String message) {
        System.out.println("[" + identifier + " INFO] " + message);
    }

    @Override
    public void logError(String message) {
        System.err.println("[" + identifier + " ERROR] " + message);
    }
}
