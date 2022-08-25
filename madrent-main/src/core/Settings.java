package core;

import managers.FileManager;

import javax.swing.*;
import java.awt.*;

/**
 * All static parts of the program is located here.
 * All things could be hardcoded, but this way the app can be resized dynamically right from here.
 */
public class Settings {
    public final static int margin = 16;
    public final static int windowWidth = 400;
    public final static int windowHeight = 500;
    public final static int finalWidth = windowWidth - margin;
    public final static int finalHeight = windowHeight - margin;

    public enum RANK{
        Guest,
        Customer,
        Admin
    }

    public final static RANK defaultRank = RANK.Guest;

    public static void setupWindow(JFrame frame, String iconName, String title, Integer width, Integer height, FileManager fileManager){
        if(width == 0 || height == 0)
            frame.setSize(Settings.windowWidth + Settings.margin, Settings.windowHeight + Settings.margin);
        else
            frame.setSize(width, height);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setIconImage(fileManager.readImage(iconName));
        frame.setTitle(title);
    }
}
