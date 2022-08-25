package scenes.Customer;

import core.Scene;
import managers.SceneManager;
import objects.CustomLabel;
import objects.CustomProgressBar;

import javax.swing.*;
import java.awt.*;

/**
 * The user who has an active construction under the way, can see its progress here.
 */
public class ViewConstruction extends Scene {
    JPanel constHolder = new JPanel();
    JLabel tasksAhead = new JLabel("Working on construction: ");

    /**
     * Constructor for setting up the scene, so the place of the progress bars is dynamic.
     *
     * @param sceneID
     * @param manager
     */
    public ViewConstruction(int sceneID, SceneManager manager) {
        super(sceneID, manager);

        LayoutManager layout = new BoxLayout(constHolder, BoxLayout.Y_AXIS);
        constHolder.setSize(380, 400);
        constHolder.setLocation(12, 40);
        constHolder.setLayout(layout);

        tasksAhead.setFont(new Font(tasksAhead.getFont().getName(), Font.BOLD, 16));
        constHolder.add(tasksAhead);

        getListOfObjects().add(constHolder);
    }

    public JPanel getDrawingPane(){
        return this.constHolder;
    }

    /**
     * On each activation the app will decide if there is any progressbar that needs to be shown for the active user.
     * It will hide any non relating information for the active user.
     */
    public void onActivate(){
        for (Component component : constHolder.getComponents()) {
            if(component instanceof CustomProgressBar)
                if(((CustomProgressBar) component).getForCustomer() == manager.getLoginManager().getActiveUser().returnWhoIAm())
                    component.setVisible(true);
                else
                    component.setVisible(false);

            if(component instanceof CustomLabel)
                if(((CustomLabel) component).getForCustomer() == manager.getLoginManager().getActiveUser().returnWhoIAm())
                    component.setVisible(true);
                else
                    component.setVisible(false);
        }
    }
}
