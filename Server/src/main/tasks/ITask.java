package main.tasks;

/**
 * This Interface is used for creating new Tasks.
 * @author Patrick Elias
 * @version 2022-05-16
 */
public interface ITask {
    String getTaskName();
    String getTaskText();
    String getTaskAnswer();
    boolean checkAnswer(String answer);
    ITask generateTask();
}
