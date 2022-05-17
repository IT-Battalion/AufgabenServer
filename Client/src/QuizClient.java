import java.io.*;
import java.net.Socket;

public class QuizClient implements Runnable {
    private final Socket socket;
    private final String uniqueID;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public QuizClient(Socket socket) {
        this.socket = socket;
        this.uniqueID = "QC-" + System.currentTimeMillis();
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void close() {
        writer.close();
        try {
            reader.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String task;
        try {
            while (!this.socket.isClosed() && (task = this.reader.readLine()) != null) {
                if (task.equalsIgnoreCase("*-*")) {
                    close();
                    System.exit(0);
                }
                System.out.println(task);
                String answer = console.readLine();
                this.writer.write(answer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
