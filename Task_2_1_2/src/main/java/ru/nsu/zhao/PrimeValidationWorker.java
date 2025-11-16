package ru.nsu.zhao;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

/**
 * Worker node for prime validation
 * 素数验证工作节点
 */
public class PrimeValidationWorker extends Thread {
    private final String serverHost;
    private final int serverPort;
    private final LoggingInterface logger;

    public PrimeValidationWorker(String host, int port, int workerId) {
        this.serverHost = host;
        this.serverPort = port;
        this.logger = new TerminalLogger("WORKER-" + workerId);
    }

    @Override
    public void run() {
        while (true) {
            try (Socket connection = new Socket(serverHost, serverPort);
                 ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(connection.getInputStream())) {

                Object received = in.readObject();
                if (received == null) {
                    logger.logInfo("No tasks available. Shutting down.");
                    break;
                }

                int[] numbers = ((NumberBatch) received).numberArray;
                boolean foundComposite = checkForComposites(numbers);
                out.writeObject(foundComposite);

                if (foundComposite) {
                    logger.logInfo("Composite number detected. Terminating.");
                    break;
                }

            } catch (IOException | ClassNotFoundException e) {
                logger.logError("Connection failure: " + e.getMessage());
                break;
            }
        }
    }

    private boolean checkForComposites(int[] numbers) {
        List<Integer> numberList = Arrays.stream(numbers).boxed().collect(Collectors.toList());
        return numberList.parallelStream().anyMatch(num -> !verifyPrime(num));
    }

    private boolean verifyPrime(int number) {
        if (number < 2) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
