import tasks.QuizClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizServer {
    private final ServerSocket serverSocket;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public QuizServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void start() {
        System.out.println("Listening for connections...");
        while (!this.serverSocket.isClosed()) {
            try {
                Socket client = this.serverSocket.accept();
                QuizClientHandler quizClientHandler = new QuizClientHandler(client);
                executorService.execute(quizClientHandler);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        close();
    }

    public void close() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
    }
}
