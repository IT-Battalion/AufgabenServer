package main.tasks;

public class FileTask implements ITask {
    private String taskName;
    private String taskText;
    private String taskAnswer;

    @Override
    public String getTaskName() {
        return this.taskName;
    }

    @Override
    public String getTaskText() {
        return this.taskText;
    }

    @Override
    public String getTaskAnswer() {
        return this.taskAnswer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.equalsIgnoreCase(this.taskAnswer);
    }

    @Override
    public ITask generateTask() {
        return this;
    }
}
