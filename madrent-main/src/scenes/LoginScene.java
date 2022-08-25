package scenes;

import core.Scene;
import managers.SceneManager;
import objects.CustomButton;
import objects.CustomPasswordField;
import objects.Sprite;
import objects.TextFieldWithPlaceholder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The default scene on app startup. This window contains forms for logging in or going to register.
 */
public class LoginScene extends Scene{
    JRadioButton dummyInput = new JRadioButton();
    Sprite loginIcon = new Sprite(0, 10, 70, 70, "loginIcon.png");

    TextFieldWithPlaceholder usernameInput = new TextFieldWithPlaceholder("Username");
    CustomPasswordField passwordInput = new CustomPasswordField("Password");
    CustomButton loginButton = new CustomButton(0, 200, 70, 30, "Login");
    CustomButton registerButton = new CustomButton(0, 240, 150, 30, "Go to registration!");

    /**
     * Setup handlers for changing scene to register and setup handlers to handle the login request from the user.
     *
     * @param sceneID
     * @param manager
     * @throws IOException
     */
    public LoginScene(int sceneID, SceneManager manager) throws IOException {
        super(sceneID, manager);

        setDefaultValues();

        //-----STATIC SPRITES-----//
        loginIcon.centerThis();
        getListOfSprites().add(loginIcon);

        //-----INTERACTIVE ELEMENTS-----//
        getListOfObjects().add(dummyInput);

        usernameInput.setBorder(BorderFactory.createEmptyBorder(0,8,0,8));
        usernameInput.setBounds(0, 100, 200, 30);
        usernameInput.centerThis();
        getListOfObjects().add(usernameInput);

        passwordInput.setBorder(BorderFactory.createEmptyBorder(0,8,0,8));
        passwordInput.setBounds(0, 150, 200, 30);
        passwordInput.centerThis();
        getListOfObjects().add(passwordInput);

        loginButton.centerThis();
        loginButton.addActionListener(e -> {
            if (manager.getLoginManager().tryLogin(usernameInput.getText(), String.valueOf(passwordInput.getPassword())))
                manager.activateScene(2);
            else
                JOptionPane.showMessageDialog(null, "Wrong username or password!");
        });
        getListOfObjects().add(loginButton);

        registerButton.centerThis();
        registerButton.addActionListener(e -> {
            manager.activateScene(4);
            System.out.println("Go to register...");
        });
        getListOfObjects().add(registerButton);
    }

    /**
     * Reset the input form each time this scene has been activated.
     */
    public void setDefaultValues(){
        usernameInput.setForeground(Color.GRAY);
        usernameInput.setText("Username");//Username//madrent

        passwordInput.setForeground(Color.GRAY);
        passwordInput.setText("Password");
    }
}
