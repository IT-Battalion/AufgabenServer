package main;

import main.tasks.ITask;

import java.io.*;
import java.net.Socket;

public class QuizClientHandler implements Runnable {
    private final Socket client;
    private final QuestionHandler questionHandler;

    public QuizClientHandler(Socket client, QuestionHandler questionHandler) {
        this.client = client;
        this.questionHandler = questionHandler;
    }

    public Socket getClient() {
        return client;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Establishing connection with new Client.");

            ITask task = questionHandler.getTask();
            System.out.println("Task: " + task.getTaskName() + "; Question: " + task.getTaskText() + "; Answer: " + task.getTaskAnswer());
            writer.write(task.getTaskText());
            writer.println();

            int i = 1;
            while (client.isConnected()) {
                String answer = reader.readLine();
                i++;
                if (answer.equalsIgnoreCase("*-*")) {
                    close(writer, reader);
                    break;
                }

                boolean correct = task.checkAnswer(answer);
                writer.write(correct ? ("Your answer is correct!") : ("Wrong answer! The correct one would be: " + task.getTaskAnswer()));
                writer.println();

                if (i == 4) {
                    close(writer, reader);
                    break;
                }

                task = questionHandler.getTask();
                System.out.println("Task: " + task.getTaskName() + "; Question: " + task.getTaskText() + "; Answer: " + task.getTaskAnswer());
                writer.write(task.getTaskText());
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(PrintWriter writer, BufferedReader reader) {
        System.out.println("Killing Thread...");
        writer.write("*-*");
        writer.println();
        writer.close();
        try {
            reader.close();
            client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
