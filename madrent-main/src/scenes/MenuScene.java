package scenes;

import core.Scene;
import managers.SceneManager;
import objects.CustomButton;
import roles.*;

import javax.swing.*;
import java.awt.*;

/**
 * The main menu, where all available actions can be accessed.
 */
public class MenuScene extends Scene{
    JLabel buttonsLabel = new JLabel("Available actions: ");

    JPanel topHalf = new JPanel();
    CustomButton orderConstruction = new CustomButton(10, 50, 150, 30, "Order construction");
    CustomButton seeConstructionProgress = new CustomButton(170, 50, 150, 30, "View progress");
    CustomButton enterAdminPanel = new CustomButton(10, 90, 170, 30, "Enter admin panel");
    CustomButton listUsers = new CustomButton(0,0,0,0, "List users");
    CustomButton seeAllConstructionProgresses = new CustomButton(190, 90, 160, 30, "View progresses");
    CustomButton goOnVacation = new CustomButton(10, 130, 140, 30, "Go on vacation");

    //BOTTOM HALF DECORATION AND INDICATORS

    public MenuScene(int sceneID, SceneManager manager) {
        super(sceneID, manager);

        topHalf.setLayout(new FlowLayout());
        topHalf.setLocation(10, 50);
        topHalf.setSize(400, 200);

        //-----INTERACTIVE ELEMENTS-----//
        buttonsLabel.setBounds(10, 10, 400, 30);
        buttonsLabel.setFont(buttonsLabel.getFont().deriveFont(15f));

        //set their size for layout manager
        orderConstruction.setMinimumSize(new Dimension(150, 30));
        seeConstructionProgress.setMinimumSize(new Dimension(150, 30));
        enterAdminPanel.setMinimumSize(new Dimension(150, 30));
        listUsers.setMinimumSize(new Dimension(150, 30));
        seeAllConstructionProgresses.setMinimumSize(new Dimension(150, 30));
        goOnVacation.setMinimumSize(new Dimension(150, 30));

        //add action listeners
        orderConstruction.addActionListener(e -> manager.activateScene(9));
        seeConstructionProgress.addActionListener(e -> manager.activateScene(10));
        enterAdminPanel.addActionListener(e -> manager.activateScene(6));
        listUsers.addActionListener(e -> manager.activateScene(5));
    }

    /**
     * On each activation decide what buttons should the user see based on its role.
     * The admins have nothing to do with the constructions, and everyone else has nothing to do with managing users.
     */
    public void onActivate(){
        if (manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Guest)
            buttonsLabel.setText("Avaiable actions for your role as Guest:");
        if (manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Customer)
            buttonsLabel.setText("Avaiable actions for your role as Customer:");
        if (manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof ConstructionWorker)
            buttonsLabel.setText("Avaiable actions for your role as ConstructionWorker:");
        if (manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof ConstructionManager)
            buttonsLabel.setText("Avaiable actions for your role as ConstructionManager:");
        if (manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Architect)
            buttonsLabel.setText("Avaiable actions for your role as Architect:");
        if (manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Admin)
            buttonsLabel.setText("Avaiable actions for your role as Admin:");

        getListOfObjects().removeAll(getListOfObjects());

        topHalf.removeAll();
        if(manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Guest)
            topHalf.add(orderConstruction);
        if(manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Customer)
            topHalf.add(seeConstructionProgress);
        if(manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof ConstructionWorker)
            topHalf.add(goOnVacation);
        if(manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Architect)
            topHalf.add(seeAllConstructionProgresses);
        if (manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Admin){
            topHalf.add(enterAdminPanel);
            topHalf.add(listUsers);
        }

        getListOfObjects().add(buttonsLabel);
        getListOfObjects().add(topHalf);
    }
}
