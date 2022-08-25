package scenes.profile;

import core.Scene;
import managers.SceneManager;
import objects.Sprite;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Display a nice profile page for each user.
 */
public class ShowProfile  extends Scene{
    JLabel name = new JLabel();
    JLabel usernameLabel = new JLabel();

    private Sprite loginSprite = new Sprite(40, 40, 150 ,150, "loginIcon.png");

    public ShowProfile(int sceneID, SceneManager manager) throws IOException {
        super(sceneID, manager);

        //-----STATIC SPRITES-----//
        getListOfSprites().add(loginSprite);

        //-----INTERACTIVE ELEMENTS-----//
        name.setBounds(210, 50, 300, 30);
        usernameLabel.setBounds(210, 90, 300, 30);
        usernameLabel.setFont(new Font(name.getFont().getName(), Font.ITALIC, 18));
        getListOfObjects().add(name);
        getListOfObjects().add(usernameLabel);
    }

    /**
     * Display a profile page and update the values on each activation.
     */
    public void onActivate(){
        name.setText(manager.getLoginManager().getActiveUser().returnWhoIAm().getFullName());
        usernameLabel.setText(manager.getLoginManager().getActiveUser().getUsername());
    }
}
