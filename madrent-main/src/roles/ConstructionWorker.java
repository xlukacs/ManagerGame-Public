package roles;

import core.Person;
import core.Task;
import objects.CustomLabel;
import objects.CustomProgressBar;
import scenes.Customer.ViewConstruction;

import javax.swing.*;

/**
 * Construction workers do the tasks slowly and in a multithreaded way.
 * When they finish, they make a request to their manager, if there is any more tasks to do.
 * Construction workers also create their own progress bars and update it regularly, when part of the task has been completed.
 */
public class ConstructionWorker extends Person {
    private double workingCoefficient = 0.5;
    private Task task;
    protected boolean isOccupied;
    private ConstructionManager taskFrom;
    private CustomProgressBar currentProgressbar;
    private CustomLabel workerName;

    /**
     * Constructor for setting up the personal information and set default state of being occupied.
     *
     * @param firstName
     * @param surName
     * @param age
     */
    public ConstructionWorker(String firstName, String surName, int age) {
        super(firstName, surName, age);
        this.isOccupied = false;
    }

    /**
     * Each time a task has been completed, the construction worker gains experience, and now will work faster.
     */
    public void increaseExperience(){
        this.setWorkingCoefficient(this.getWorkingCoefficient() + 0.1);
    }

    @Override
    public void isWorker(){
        System.out.println("I am a worker of the company.");
    }

    private double getWorkingCoefficient() {
        return workingCoefficient;
    }

    private void setWorkingCoefficient(double workingCoefficient) {
        this.workingCoefficient = workingCoefficient;
    }

    public boolean isFree(){
        return !isOccupied;
    }

    /**
     * Create new progress bars and label and attach them to the GUI. Also start the task on a new thread.
     * After that if the active user is the user who ordered the construction, then show this progress to them.
     *
     * @param task What task got assigned to this construction worker
     * @param taskFrom Who handed out the task
     */
    public void assignTask(Task task, ConstructionManager taskFrom){
        this.task = task;
        this.isOccupied = true;
        this.taskFrom = taskFrom;
        this.increaseExperience();

        currentProgressbar = new CustomProgressBar(0, 100, taskFrom.getUserOfConstruction()); //min, max values
        workerName = new CustomLabel(this.getFullName() + " - level of worker: " + (int)(this.getWorkingCoefficient() / 0.3), taskFrom.getUserOfConstruction());
        if(taskFrom.manager.getScene(10) instanceof ViewConstruction) { //we know that this is the scene to display a singular construction
            JPanel panel = ((ViewConstruction) taskFrom.manager.getScene(10)).getDrawingPane();

            if(taskFrom.getUserOfConstruction() == taskFrom.manager.getLoginManager().getActiveUser().returnWhoIAm()) {
                panel.add(workerName);
                panel.add(currentProgressbar);
            }

            taskFrom.manager.updateScene();
        }

        task.start(this, workingCoefficient);
    }

    /**
     * Callback function for notifying the manager, that the handed out task has been completed and this worker is now free.
     */
    public void notifyBoss(){
        this.isOccupied = false;
        taskFrom.notifyThatReady(this, currentProgressbar, workerName);
    }

    public JProgressBar getCurrentProgressbar(){
        return this.currentProgressbar;
    }
}
