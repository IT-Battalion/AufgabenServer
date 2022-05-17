package tasks;

public class RandomCalculationTask implements ITask {
    private String taskText = "Solve: ";
    private String taskAnswer;

    @Override
    public String getTaskName() {
        return "Random Calculation Task";
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
        double n1 = (Math.random() * 100000);
        double n2 = (Math.random() * 100000);
        String operation = this.getRandomOperator();
        this.taskText = n1 + " " + operation + " " + n2;
        double solution;
        switch (operation) {
            case "-": {
                solution = n1 - n2;
                break;
            }
            case "*": {
                solution = n1 * n2;
                break;
            }
            case "/": {
                solution = n1 / n2;
                break;
            }
            default: {
                solution = n1 + n2;
                break;
            }
        }
        this.taskAnswer = String.valueOf(solution);
        return this;
    }

    public String getRandomOperator() {
        int n = 1 + (int) (Math.random() * 4);
        switch (n) {
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            default:
                return "+";
        }
    }
}
