package scenes.guest;

import core.*;
import managers.SceneManager;
import objects.CustomButton;
import objects.TextFieldWithPlaceholder;
import roles.Customer;
import roles.Guest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Guests and customers can order a new construction in this scene.
 */
public class OrderConstruction extends Scene implements ActionListener {
    private boolean areRulesAccepted;

    JLabel title = new JLabel("Fill out the form below to order a construction!");
    JLabel acceptRules = new JLabel("Agree to terms and risks!");
    JCheckBox ruleBox = new JCheckBox();

    TextFieldWithPlaceholder constructionName = new TextFieldWithPlaceholder("Enter construction name here...");
    JLabel sizeLabel = new JLabel("Construction size: ");
    String [] constructionSizes = {"<100m^2","101-500m^2","501-2000m^2",">2001m^2"};
    TextFieldWithPlaceholder constructionNotes = new TextFieldWithPlaceholder("Enter any additional notes here...");
    JComboBox sizeList = new JComboBox(constructionSizes);
    CustomButton orderButton = new CustomButton(0, 350, 150, 30, "Order construction!");

    /**
     * Constructor for setting up handlers and GUI elements.
     *
     * @param sceneID
     * @param manager
     */
    public OrderConstruction(int sceneID, SceneManager manager) {
        super(sceneID, manager);

        this.areRulesAccepted = false;

        //-----INTERACTIVE ELEMENTS-----//
        title.setBounds(10, 10, 300, 30);
        title.setFont(title.getFont().deriveFont(15f));
        getListOfObjects().add(title);

        acceptRules.setBounds(35, 300, 300, 30);
        acceptRules.setFont(acceptRules.getFont().deriveFont(15f));
        getListOfObjects().add(acceptRules);

        constructionName.setBounds(Settings.windowWidth / 2 - 150, 50, 300, 30);
        getListOfObjects().add(constructionName);

        constructionNotes.setBounds(Settings.windowWidth / 2 - 150, 90, 300, 30);
        getListOfObjects().add(constructionNotes);

        sizeLabel.setBounds(40, 140, 120, 30);
        getListOfObjects().add(sizeLabel);
        sizeList.setBounds(175, 140, 200, 30);
        getListOfObjects().add(sizeList);

        ruleBox.setBounds(10, 305, 20, 20);
        getListOfObjects().add(ruleBox);

        orderButton.centerThis();
        getListOfObjects().add(orderButton);

        sizeList.addActionListener(this);
        ruleBox.addActionListener(this);
        orderButton.addActionListener(this);
    }

    /**
     * Overridden method for detecting what button has been clicked or selected.
     * When ordering, the app will check weather the active user is a guest or a customer and it will proceed accordingly.
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JComboBox)
            System.out.println(((JComboBox<?>) e.getSource()).getSelectedItem());

        if (e.getSource() instanceof JCheckBox)
            this.areRulesAccepted = ((JCheckBox) e.getSource()).isSelected();

        if (e.getSource() == orderButton){
            if (this.areRulesAccepted && !this.constructionName.getText().equals("")) {
                //get the construction
                Construction tryConstruction = manager.getLoginManager().getTheArchitectItself().createConstruction(this.constructionName.getText(), this.constructionNotes.getText(), this.sizeList.getSelectedItem().toString(), manager);
                if(tryConstruction == null)
                    JOptionPane.showMessageDialog(null, "Order failed!");
                else {
                    if (manager.getLoginManager().getActiveUser().returnWhoIAm() instanceof Guest) {
                        //recast the user to customer if it is a guest
                        try {
                            User currentActiveUser = manager.getLoginManager().getActiveUser();
                            manager.getLoginManager().setActiveUser(
                                    manager.getLoginManager().changeUserData(
                                            currentActiveUser,
                                            manager.getLoginManager().getFileManager().createUser(
                                                    currentActiveUser.getUsername(),
                                                    currentActiveUser.getPassword(),
                                                    manager.getLoginManager().getFileManager().createRole(
                                                            new Customer("", "", 0),
                                                            currentActiveUser.returnWhoIAm().getName().getSurName(),
                                                            currentActiveUser.returnWhoIAm().getName().getFirstName()
                                                    )
                                            )
                                    )
                            );
                        } catch (IOException | FileErrorException ioException) {
                            ioException.printStackTrace();
                        }
                    }

                    //add the construction to the user who ordered it
                    ((Customer) manager.getLoginManager().getActiveUser().returnWhoIAm()).confirmOrder(tryConstruction);
                }
            }
        }
    }
}
