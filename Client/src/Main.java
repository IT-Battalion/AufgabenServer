import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Das ist der Client, welcher sich zum Server verbindet und dann zufällige Fragen
 * erhält.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Client...");
        String port = args.length == 0 ? "2445" : args[0];
        System.out.println("Port: " + port);
        String host = args.length <= 1 ? "localhost" : args[1];
        System.out.println("Host: " + host);

        try (
                Socket socket = new Socket(InetAddress.getByName(host), Integer.parseInt(port));
        ) {
            QuizClient client = new QuizClient(socket);
            client.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
