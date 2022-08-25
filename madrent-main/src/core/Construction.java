package core;

import managers.SceneManager;
import objects.CustomLabel;
import objects.CustomProgressBar;
import roles.Architect;
import roles.ConstructionManager;
import roles.Customer;
import scenes.Customer.ViewConstruction;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This class contains the blueprint of each construction.
 * It has defined task and GUI elements in it.
 */
public class Construction {
    private double progress;
    private String name;

    private ArrayList<Task> tasks;
    private ConstructionManager cManager;

    private Architect observer;
    private Customer customer;

    private SceneManager manager;

    private CustomLabel constName;
    private JPanel drawingBoard;
    private CustomProgressBar overallComplete;

    private int step;


    /**
     * Constructor for setting up the construction with the required data and assigning it to a construction manager.
     * Also create and display a progressbar indicating the overall progress.
     *
     * @param tasks The ArrayList of Tasks to do
     * @param cManager The construction manager who will control the flow of the job handling
     * @param theObserver The architect, who created this construction
     * @param manager The sceneManager for permission to draw on the pane
     * @param name The name of the construction from user input
     */
    public Construction(ArrayList<Task> tasks, ConstructionManager cManager, Architect theObserver, SceneManager manager, String name){
        this.tasks = tasks;
        this.cManager = cManager;
        this.observer = theObserver;
        this.manager = manager;
        this.name = name;

        //calculate how much % is one completed task
        step = 100/tasks.size();

        //set the main data of the construction
        constName = new CustomLabel("Construction (" + this.name + ") progress:");
        progress = 0;
        overallComplete = new CustomProgressBar(0, 100);
        overallComplete.setValue((int)progress);

        //get the drawing panel and add the created components to it later if the user matches
        if(manager.getScene(10) instanceof ViewConstruction)
            drawingBoard = ((ViewConstruction)manager.getScene(10)).getDrawingPane();

        drawingBoard.add(getConstName());
        drawingBoard.add(overallComplete);


        //also give this construction to the determined construction manager
        this.cManager.setConstruction(this);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public SceneManager getManager() {
        return manager;
    }

    public ConstructionManager getcManager(){return cManager;}

    /**
     * Callback method from construction manager to update the overall progressbar.
     * After setting the new value, update the scene.
     *
     * @param tasksRemaining Callback value from construction manager
     */
    public void updateProgress(int tasksRemaining){
        //decide if we are at max progress, then update the main progressbar and update the scene graphics
        if(tasksRemaining == 0)
            this.progress = 100;
        else
            this.progress += step;

        overallComplete.setValue((int)progress);
        manager.updateScene();
    }

    /**
     * Method for deleting every remaining progressbar and label from the displaying scenes.
     */
    public void clearConstruction(){
        //clear construction labels, then update the scene
        if(customer == manager.getLoginManager().getActiveUser().returnWhoIAm()) {
            drawingBoard.remove(getConstName());
            drawingBoard.remove(overallComplete);
            manager.updateScene();
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    /**
     * Method called from customer, to acknowledge that the construction has been created and it can be started.
     */
    public void confirmReadyFromCustomer(){
        getConstName().setForCustomer(customer);
        overallComplete.setForCustomer(customer);

        cManager.confirmReadyFromCustomer();
    }

    public CustomLabel getConstName() {
        return constName;
    }

    public String getName() {
        return name;
    }
}
