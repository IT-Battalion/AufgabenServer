package main;

import main.tasks.ITask;
import main.tasks.RandomCalculationTask;
import main.tasks.SchoolNameTask;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuizClientHandler implements Runnable {
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
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Establishing connection with new Client.");

           /* List<ITask> questions = new ArrayList<>();
            File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("questions.json")).toURI());
            questions = (List<ITask>) parse(file);
            for (ITask tt :
                    questions) {
                System.out.println(tt);
            }*/

            ITask task = System.currentTimeMillis() % 2 == 0 ? new RandomCalculationTask().generateTask() : new SchoolNameTask().generateTask();
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

                task = System.currentTimeMillis() % 2 == 0 ? new RandomCalculationTask().generateTask() : new SchoolNameTask().generateTask();
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

    public Map parse(File f) throws ScriptException, IOException {
        ScriptEngine scriptEngine;
        String json;

        ScriptEngineManager sem = new ScriptEngineManager();
        scriptEngine = sem.getEngineByName("javascript");
        json = new String(Files.readAllBytes(f.toPath()));

        String script = "Java.asJSONCompatible(" + json + ")";
        Object result = scriptEngine.eval(script);
        Map contents = (Map) result;
        return contents;
        /*contents.forEach((k,v) ->{
            System.out.println("Key => "+k +" value =>"+contents.get(k));
        });

        List data = (List) contents.get("data");
        data.forEach(d ->{
            Map matchDetail = (Map) d;
            matchDetail.forEach((k,v) ->{
                System.out.println("Key => "+k +" value =>"+matchDetail.get(k));
            });
        });*/
    }

}
