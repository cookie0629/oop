package ru.nsu.zhao;

/**
 * Distributed Prime Number Checker System
 * 分布式素数检查系统
 */
public class DistributedPrimeChecker {
    public static void main(String[] args) throws InterruptedException {
        final int SERVER_PORT = 54321;
        final int NUM_COUNT = 1000000;
        final int SEGMENT_SIZE = 100000;
        final int WORKER_NUM = 5;

        // Generate random numbers for testing / 生成测试用的随机数
        int[] testNumbers = new java.util.Random().ints(NUM_COUNT, 2, 10000).toArray();

        // Create a server instance / 创建服务器实例
        PrimeValidationServer server = new PrimeValidationServer(SERVER_PORT, testNumbers, SEGMENT_SIZE);
        server.start();

        // Wait for server initialization / 等待服务器初始化
        Thread.sleep(300);

        // Start multiple workers / 启动多个工作节点
        for (int i = 1; i <= WORKER_NUM; i++) {
            new PrimeValidationWorker("127.0.0.1", SERVER_PORT, i).start();
        }
    }
}