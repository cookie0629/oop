package ru.nsu.zhao;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Server for distributing prime validation tasks
 * 素数验证任务分发服务器
 */
public class PrimeValidationServer extends Thread {
    private final int portNumber;
    private final Queue<NumberBatch> pendingTasks = new ConcurrentLinkedQueue<>();
    private final ExecutorService taskExecutors = Executors.newCachedThreadPool();
    private volatile boolean compositeFound = false;
    private ServerSocket listenerSocket;
    private final LoggingInterface logger;

    public PrimeValidationServer(int port, int[] numbers, int batchSize) {
        this.portNumber = port;
        this.logger = new TerminalLogger("SERVER");

        // Split numbers into batches / 将数字分割成批次
        for (int i = 0; i < numbers.length; i += batchSize) {
            int endIndex = Math.min(i + batchSize, numbers.length);
            pendingTasks.add(new NumberBatch(Arrays.copyOfRange(numbers, i, endIndex)));
        }
    }

    @Override
    public void run() {
        try {
            listenerSocket = new ServerSocket(portNumber);
            logger.logInfo("Server started on port " + portNumber);

            while (!compositeFound && !pendingTasks.isEmpty()) {
                try {
                    Socket workerSocket = listenerSocket.accept();
                    taskExecutors.submit(() -> {
                        processWorkerConnection(workerSocket);
                        verifyCompletion();
                    });
                } catch (SocketException e) {
                    logger.logInfo("Connection closed properly");
                    break;
                } catch (IOException e) {
                    logger.logError("Connection error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.logError("Server startup failed: " + e.getMessage());
        } finally {
            cleanupResources();
            logger.logInfo("Server shutdown complete");
        }
    }

    private void processWorkerConnection(Socket socket) {
        NumberBatch currentBatch = null;
        try (ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream())) {

            currentBatch = pendingTasks.poll();
            if (currentBatch == null) {
                outStream.writeObject(null);
                return;
            }

            outStream.writeObject(currentBatch);

            Boolean hasComposite = (Boolean) inStream.readObject();
            if (hasComposite != null && hasComposite) {
                compositeFound = true;
            }

        } catch (Exception e) {
            logger.logError("Worker error: " + e.getMessage());
            if (currentBatch != null) {
                pendingTasks.add(currentBatch);
                logger.logInfo("Batch requeued for processing");
            }
        }
    }

    private void verifyCompletion() {
        if (compositeFound) {
            try {
                listenerSocket.close();
                taskExecutors.shutdownNow();
            } catch (IOException e) {
                logger.logError("Shutdown error: " + e.getMessage());
            }
        }
    }

    private void cleanupResources() {
        try {
            if (listenerSocket != null && !listenerSocket.isClosed()) {
                listenerSocket.close();
            }
        } catch (IOException e) {
            logger.logError("Resource cleanup error: " + e.getMessage());
        }
        taskExecutors.shutdownNow();
    }
}
