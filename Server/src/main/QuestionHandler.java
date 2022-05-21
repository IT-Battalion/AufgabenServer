package main;

import main.tasks.ITask;
import main.tasks.RandomCalculationTask;
import main.tasks.SchoolNameTask;

import java.util.ArrayList;
import java.util.List;

public class QuestionHandler implements Runnable {
    private final List<ITask> questions = new ArrayList<>(20);
    boolean running = true;

    @Override
    public void run() {
        while (running) {
            if (questions.size() < 20) {
                questions.add(generateTask());
                this.notifyAll();
            }
        }
    }

    private synchronized ITask generateTask() {
        return System.currentTimeMillis() % 2 == 0 ? new RandomCalculationTask().generateTask() : new SchoolNameTask().generateTask();
    }

    public synchronized ITask getTask() {
        if (questions.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {}
        }
        ITask task = questions.get(0);
        this.questions.remove(task);
        return task;
    }

    public void shutdown() {
        running = false;
    }
}
