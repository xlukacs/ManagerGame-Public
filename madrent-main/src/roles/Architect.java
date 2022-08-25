package roles;

import core.Construction;
import core.Task;
import managers.SceneManager;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The architect manages every construction and therefore there is only one instance.
 * This role will create and distribute constructions.
 */
public class Architect extends ConstructionManager{
    enum Size{
        SMALL,
        MEDIUM,
        LARGE,
        HUGE
    }
    private Size constructionSize;

    public Architect(String firstName, String surName, int age) {
        super(firstName, surName, age);
    }

    /**
     * Method for creating constructions and its tasks, depending on the selected size.
     * When the construction is ready, then find a construction manager, who is not occupied and assign this job to it.
     *
     * @param constName What construction name did the user type in the form
     * @param notes What notes did the user leave
     * @param size How big of a construction did the user select
     * @param manager Where will the construction be rendered
     * @return Construction
     */
    public Construction createConstruction(String constName, String notes, String size, SceneManager manager){
        //AtomicBoolean temp = new AtomicBoolean(false);

        switch (size){
            case "<100m^2":
                this.constructionSize = Size.SMALL;
                break;
            case "101-500m^2":
                this.constructionSize = Size.MEDIUM;
                break;
            case "501-2000m^2":
                this.constructionSize = Size.LARGE;
                break;
            case ">2001m^2":
                this.constructionSize = Size.HUGE;
                break;
        }

        System.out.println("Determining manager!");
        /*manager.getLoginManager().getConstManagers().forEach(constructionManager -> {
            if (constructionManager.isFree()){
                ArrayList<Task> tasksToDo = new ArrayList<>();

                //Size.SMALL
                if (this.constructionSize == Size.SMALL)
                    for (int i = 0; i < 10; i++)
                        tasksToDo.add(new Task(i + ". task", 1, 5));

                //Size.MEDIUM
                if (this.constructionSize == Size.MEDIUM)
                    for (int i = 0; i < 12; i++)
                        tasksToDo.add(new Task(i + ". task", 2, 5));

                //Size.LARGE
                if (this.constructionSize == Size.LARGE)
                    for (int i = 0; i < 20; i++)
                        tasksToDo.add(new Task(i + ". task", 3, 10));

                //Size.HUGE
                if (this.constructionSize == Size.HUGE)
                    for (int i = 0; i < 100; i++)
                        tasksToDo.add(new Task(i + ". task", 1, 15));

                //Add workers to manager
                manager.getLoginManager().getConstWorkers().forEach(constructionWorker -> {
                    if(constructionWorker.isFree())
                        constructionManager.addWorker(constructionWorker);
                });
                constructionManager.setTasks(tasksToDo, manager);

                Construction construction = new Construction(tasksToDo, constructionManager, this);

                temp.set(true);
            }else {
                //TODO notify user, that there is no free capacity to make constructions
                System.out.println("This one is not free!");
                temp.set(false);
            }
        });*/

        for (ConstructionManager cManager : manager.getLoginManager().getConstManagers()) {
            if (cManager.isFree()) {
                ArrayList<Task> tasksToDo = new ArrayList<>();

                //Size.SMALL
                if (this.constructionSize == Size.SMALL)
                    for (int i = 0; i < 10; i++)
                        tasksToDo.add(new Task(i + ". task", 1, 5));

                //Size.MEDIUM
                if (this.constructionSize == Size.MEDIUM)
                    for (int i = 0; i < 12; i++)
                        tasksToDo.add(new Task(i + ". task", 2, 5));

                //Size.LARGE
                if (this.constructionSize == Size.LARGE)
                    for (int i = 0; i < 20; i++)
                        tasksToDo.add(new Task(i + ". task", 3, 10));

                //Size.HUGE
                if (this.constructionSize == Size.HUGE)
                    for (int i = 0; i < 100; i++)
                        tasksToDo.add(new Task(i + ". task", 1, 15));

                //Add workers to manager
                manager.getLoginManager().getConstWorkers().forEach(constructionWorker -> {
                    if (constructionWorker.isFree())
                        cManager.addWorker(constructionWorker);
                });

                Construction construction = new Construction(tasksToDo, cManager, this, manager, constName);

                return construction;
            } else
                System.out.println("This one is not free!");
        }

        JOptionPane.showMessageDialog(null, "There is no free capacity for your order, try again later.");

        return null;
    }
}
