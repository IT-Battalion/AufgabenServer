import java.io.IOException;
import java.net.ServerSocket;

/**
 * Die Main Klasse für den Server. Wird als erstes Argument kein Port beim
 * starten Übergeben wird der Standard Port 2445 verwendet.
 *
 * @author Patrick Elias
 * @version 2022-05-16
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Server...");
        String port = args.length == 0 ? "2445" : args[0];
        System.out.println("Port: " + port);
        try (
                ServerSocket server = new ServerSocket(Integer.parseInt(port));
        ) {
            QuizServer quizServer = new QuizServer(server);
            quizServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
