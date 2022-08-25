package roles;

import core.Construction;
import core.Task;
import core.User;
import managers.SceneManager;
import objects.CustomLabel;
import objects.CustomProgressBar;
import scenes.Customer.ViewConstruction;

import java.util.ArrayList;

/**
 * Construction manager will hand out tasks to construction workers, which it got from the architect.
 * This class has methods for managing, starting and cleaning up constructions.
 * This class also has callback function from the construction worker and to the the architect and to the customer.
 */
public class ConstructionManager extends ConstructionWorker{
    SceneManager manager;

    private Construction construction;

    private ArrayList<ConstructionWorker> workers = new ArrayList<>();

    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Constructor for setting up and handling constructions, after creating the ,,person,, behind it.
     * Also change the state of the manager to not occupied.
     *
     * @param firstName
     * @param surName
     * @param age
     */
    public ConstructionManager(String firstName, String surName, int age) {
        super(firstName, surName, age);
        this.isOccupied = false;
    }

    public void addWorker(ConstructionWorker worker){
        this.getWorkers().add(worker);
    }

    private ArrayList<ConstructionWorker> getWorkers() {
        return workers;
    }


    /**
     * Method for assigning the final construction blueprint to the construction manager.
     *
     * @param construction The blueprint of the construction
     */
    public void setConstruction(Construction construction){
        this.construction = construction;
        this.tasks = this.construction.getTasks();
        this.manager = this.construction.getManager();
        this.isOccupied = true;
    }

    public Customer getUserOfConstruction(){
        return construction.getCustomer();
    }

    public void confirmReadyFromCustomer(){
        this.beginConstruction();
    }

    @Override
    public void isWorker() {
        System.out.println("Yes I am a worker of the company.");
    }

    /**
     * Find free construction workers and assign tasks to them from the construction blueprint.
     * Remove handed out tasks and start them.
     */
    private void beginConstruction(){
        getWorkers().forEach(constructionWorker -> {
            if (constructionWorker.isFree()) {
                constructionWorker.assignTask(tasks.get(0), this);
                tasks.remove(0);
            }
        });
    }

    /**
     * Manage progress bars and hand out new tasks to workers, after they triggered this callback method.
     * When no tasks are left, clean up the whole construction and the GUI.
     *
     * @param workerReady Who finished the task
     * @param workingOnBar What was this workers progressBar
     * @param workerName What was this workers label in the GUI
     */
    public void notifyThatReady(ConstructionWorker workerReady, CustomProgressBar workingOnBar, CustomLabel workerName){
        construction.updateProgress(tasks.size());

        if(manager.getScene(10) instanceof ViewConstruction && workingOnBar.getForCustomer() == manager.getLoginManager().getActiveUser().returnWhoIAm()) { //we know that this is the scene to display a singular construction
            ((ViewConstruction) manager.getScene(10)).getDrawingPane().remove(workingOnBar);
            ((ViewConstruction) manager.getScene(10)).getDrawingPane().remove(workerName);

            manager.updateScene();
        }

        if (tasks.size() > 0) {
            workerReady.assignTask(tasks.get(0), this);
            tasks.remove(0);
        }else {
            this.isOccupied = false;
            construction.clearConstruction();
        }
    }
}
