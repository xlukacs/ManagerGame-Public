package core;

import roles.ConstructionWorker;

/**
 * Tasks are part of the construction.
 * They are getting assigned to construction workers.
 * They are run in parallel using threads.
 */
public class Task extends Thread{
    private Thread t;
    private int stage;
    private String description;
    private int hardness;
    private double completed;
    private ConstructionWorker taskOfWho;
    private double speedMultiplier;

    /**
     * Constructor for setting the default values and hardness of each task.
     *
     * @param desc User given description of the construction
     * @param stage How much steps does the task have
     * @param hardness How hard is each step
     */
    public Task(String desc, int stage, int hardness){
        this.description = desc;
        this.stage = stage;
        this.hardness = hardness;
        this.completed = 0f;
    }

    /**
     * Method fro setting information about the worker, who this task got assigned to.
     *
     * @param taskOfWho The construction worker who handles this task
     * @param speedMultiplier How fast does the construction worker work
     */
    public void start (ConstructionWorker taskOfWho, double speedMultiplier) {
        this.taskOfWho = taskOfWho;
        this.speedMultiplier = speedMultiplier;
        if (t == null) {
            t = new Thread (this);
            t.start();
        }
    }

    /**
     * Method which runs on another thread.
     * Completes the task slowly, updates the progress and makes a callback to the construction worker.
     */
    public void run(){
        while (this.completed < 100){
            this.completed += (100/(hardness*stage))*this.speedMultiplier;

            if (this.completed > 100)
                this.completed = 100;
            taskOfWho.getCurrentProgressbar().setValue((int) this.completed);

            try {
                Thread.sleep(1000L * stage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Notify the manager who assigned the task, that this task has been completed
        this.taskOfWho.notifyBoss();
    }
}
