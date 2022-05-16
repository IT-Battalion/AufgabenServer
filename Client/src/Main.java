import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Das ist der Client, welcher sich zum Server verbindet und dann zufällige Fragen
 * erhält.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting Client...");
        String port = args.length == 0 ? "2445" : args[0];
        System.out.println("Port: " + port);
        ServerSocket server = new ServerSocket(Integer.parseInt(port));
        System.out.println("Waiting for Server connection...");
        Socket socket = server.accept();
        System.out.println("Connection to Server established.");
    }
}
