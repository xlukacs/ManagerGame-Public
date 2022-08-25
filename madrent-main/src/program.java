import core.Settings;
import managers.FileManager;
import managers.SceneManager;
import managers.UserManager;
import objects.CustomLayeredPane;

import javax.swing.*;
import java.io.IOException;

public class program {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CustomLayeredPane pane = new CustomLayeredPane();

        FileManager fileManager = new FileManager();
        UserManager userManager = new UserManager(fileManager);
        SceneManager sceneManager = new SceneManager(pane, userManager);

        JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.add(pane);

        sceneManager.setFrame(mainWindow);

        Settings.setupWindow(mainWindow, "icon2.png", "Doge construction manager", 0 , 0, fileManager);
    }
}
