package scenes.admin;


import core.Scene;
import managers.SceneManager;
import objects.inc.Row;

import javax.swing.*;
import java.awt.*;

import static core.Settings.windowWidth;


/**
 * The scene for listing users and changing or deleting their records.
 */
public class ManageUsers extends Scene{
    JPanel userList = new JPanel();

    public ManageUsers(int sceneID, SceneManager manager) {
        super(sceneID, manager);

        //set this scene to auto-layout
        userList.setLayout(new BoxLayout(userList, BoxLayout.Y_AXIS));
        userList.setSize(windowWidth, 300);
        userList.setLocation(9, 0);
    }

    /**
     * Make this scene update the table contents each time it gets loaded.
     * This is basically the refresh function, by clearing and re-populating the table with new rows.
     */
    public void onActivate(){
        //clear all objects
        clearObjects();
        userList.removeAll();

        //add rows to screen
        manager.getLoginManager().getUsers().forEach(user -> {
            if(userList.getComponents().length%2 == 0)
                userList.add(new Row(this, manager.getLoginManager(), user, Row.Direction.HORIZONTAL, Color.GRAY));
            else
                userList.add(new Row(this, manager.getLoginManager(), user, Row.Direction.HORIZONTAL, Color.DARK_GRAY));
        });


        getListOfObjects().add(userList);
    }
}
