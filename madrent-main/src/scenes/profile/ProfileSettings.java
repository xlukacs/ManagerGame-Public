package scenes.profile;

import core.FileErrorException;
import core.Scene;
import core.Settings;
import managers.SceneManager;
import objects.CustomButton;

import javax.swing.*;
import java.io.IOException;

/**
 * Users can set their login credentials and names here.
 */
public class ProfileSettings extends Scene{
    JLabel surnameLabel = new JLabel("Surname: ");
    JLabel lastnameLabel = new JLabel("Lastname: ");
    JLabel usernameLabel = new JLabel("Username: ");
    JLabel passwordLabel = new JLabel("Password: ");
    JTextField surname = new JTextField();
    JTextField lastname = new JTextField();
    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();

    CustomButton saveButton = new CustomButton(0, 300, 180, 30, "Save changes!");

    public ProfileSettings(int sceneID, SceneManager manager) {
        super(sceneID, manager);

        //-----INTERACTIVE ELEMENTS-----//
        surnameLabel.setBounds(Settings.windowWidth/2 - 150, 10, 150, 30);
        lastnameLabel.setBounds(Settings.windowWidth/2 - 150, 50, 150, 30);
        usernameLabel.setBounds(Settings.windowWidth/2 - 150, 90, 150, 30);
        passwordLabel.setBounds(Settings.windowWidth/2 - 150, 130, 150, 30);
        getListOfObjects().add(surnameLabel);
        getListOfObjects().add(lastnameLabel);
        getListOfObjects().add(usernameLabel);
        getListOfObjects().add(passwordLabel);
        surname.setBounds(Settings.windowWidth/2, 10, 150, 30);
        lastname.setBounds(Settings.windowWidth/2, 50, 150, 30);
        username.setBounds(Settings.windowWidth/2, 90, 150, 30);
        password.setBounds(Settings.windowWidth/2, 130, 150, 30);
        getListOfObjects().add(surname);
        getListOfObjects().add(lastname);
        getListOfObjects().add(username);
        getListOfObjects().add(password);

        saveButton.centerThis();
        saveButton.addActionListener(e -> {
            try {
                manager.getLoginManager().setActiveUser(
                    manager.getLoginManager().changeUserData(   manager.getLoginManager().getActiveUser(),
                                                                manager.getLoginManager().getFileManager().createUser(username.getText(), String.valueOf(password.getPassword()),
                                                                manager.getLoginManager().getFileManager().createRole(manager.getLoginManager().getActiveUser().returnWhoIAm(), surname.getText(), lastname.getText())))
                );
            } catch (IOException | FileErrorException ex) {
                ex.printStackTrace();
            }
            manager.activateScene(7);
        });
        getListOfObjects().add(saveButton);
    }

    /**
     * Set the default values on each activation of the input fields, based on the information what the active user has.
     */
    public void setDefaultValues(){
        surname.setText(manager.getLoginManager().getActiveUser().returnWhoIAm().getName().getSurName());
        lastname.setText(manager.getLoginManager().getActiveUser().returnWhoIAm().getName().getFirstName());
        username.setText(manager.getLoginManager().getActiveUser().getUsername());
        password.setText(manager.getLoginManager().getActiveUser().getPassword());
    }
}
