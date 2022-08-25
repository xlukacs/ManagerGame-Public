package scenes;

import core.FileErrorException;
import core.Scene;
import managers.SceneManager;
import objects.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The scene for registering a new user to the system.
 */
public class RegisterScene extends Scene{
    private Sprite loginSprite = new Sprite(20, 20, 50 ,50, "loginIcon.png");

    private CustomLabel username = new CustomLabel("Enter username: ");
    private TextFieldWithPlaceholder usernameInput = new TextFieldWithPlaceholder("Username");
    private CustomLabel password = new CustomLabel("Enter password: ");
    private CustomPasswordField passwordInput = new CustomPasswordField("Password");

    private CustomButton registerButton = new CustomButton(0, 250, 150, 30, "Register");
    private CustomButton backButton = new CustomButton(0, 290, 150, 30, "Back");

    /**
     * Constructor for setting the handlers for buttons and user actions.
     * Also setup the default GUI.
     *
     * @param sceneID
     * @param manager
     * @throws IOException
     */
    public RegisterScene(int sceneID, SceneManager manager) throws IOException {
        super(sceneID, manager);

        setDefaultValues();

        //-----STATIC SPRITES-----//
        loginSprite.centerThis();
        getListOfSprites().add(loginSprite);

        //-----INTERACTIVE ELEMENTS-----//
        username.setBounds(0, 90, 100, 30);
        username.centerThis();
        getListOfObjects().add(username);
        usernameInput.setBounds(0, 130, 200, 30);
        usernameInput.centerThis();
        getListOfObjects().add(usernameInput);
        password.setBounds(0, 170, 100, 30);
        password.centerThis();
        getListOfObjects().add(password);
        passwordInput.setBounds(0, 210, 200, 30);
        passwordInput.centerThis();
        getListOfObjects().add(passwordInput);

        registerButton.centerThis();
        registerButton.addActionListener(e -> {
            try {
                if(manager.getLoginManager().tryRegister(usernameInput.getText(), String.valueOf(passwordInput.getPassword())))
                    manager.activateScene(1);
                else
                    JOptionPane.showMessageDialog(null, "Unsuccessful registration!");
            } catch (IOException | FileErrorException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        });
        getListOfObjects().add(registerButton);

        backButton.centerThis();
        backButton.addActionListener(e -> manager.activateScene(1));
        getListOfObjects().add(backButton);
    }

    /**
     * Reset form on each activation.
     */
    public void setDefaultValues(){
        usernameInput.setForeground(Color.GRAY);
        usernameInput.setText("Username");

        passwordInput.setForeground(Color.GRAY);
        passwordInput.setText("Password");
    }
}
