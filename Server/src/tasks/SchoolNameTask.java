package tasks;

public class SchoolNameTask implements ITask {
    private String taskText;
    private String taskAnswer;

    @Override
    public String getTaskName() {
        return "School Name Task";
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
        return answer.equalsIgnoreCase(taskAnswer);
    }

    @Override
    public ITask generateTask() {
        this.taskText = "What is the schools short name?";
        this.taskAnswer = "TGM";
        return this;
    }
}
