package tasks;

import java.io.*;
import java.net.Socket;

public class QuizClientHandler implements Runnable{
    private final Socket client;

    public QuizClientHandler(Socket client) {
        this.client = client;
    }

    public Socket getClient() {
        return client;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Establishing connection with new Client.");
            for (int i = 0; i != 3; i++) {
                ITask task = System.currentTimeMillis() % 2 == 0 ? new RandomCalculationTask().generateTask() : new SchoolNameTask().generateTask();
                System.out.println("Task: " + task.getTaskName() + "; Question: " + task.getTaskText() + "; Answer: " + task.getTaskAnswer());
                writer.write(task.getTaskText());
                String answer;
                while ((answer = reader.readLine()) != null) {
                    boolean correct = task.checkAnswer(answer);
                    writer.write(correct ? ("Your answer is correct!") : ("Wrong answer! The correct one would be: " + task.getTaskAnswer()));
                }
            }
            writer.write("*-*");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
