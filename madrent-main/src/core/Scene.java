package core;

import managers.SceneManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Scenes are parts of the GUI. They are the frame which the program will render on the main drawing pane.
 * It contains information about its elements, sprites, menuBars.
 * It also contains methods for the scene manager to run while changing scenes.
 * There is a method to be run before,during and after the activation of the scene.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Scene implements IScene{
    private final Integer[] forbiddenScenes = new Integer[]{1,4};//1 - loginScene, 4 - registerScene
    List<Integer> banList = new ArrayList<>(Arrays.asList(forbiddenScenes));

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menu1 = new JMenu(" Settings ");
    private final JMenu menu4 = new JMenu(" View ");
    private final JMenu menu2 = new JMenu(" Profile ");
    private final JMenu menu3 = new JMenu(" Admin panel ");
    private final JMenuItem item1 = new JMenuItem(" Logout                      ");
    private final JMenuItem item2 = new JMenuItem(" Home");
    private final JMenuItem item3 = new JMenuItem(" Exit");
    private final JMenuItem item8 = new JMenuItem(" Go to home screen");
    private final JMenuItem item4 = new JMenuItem(" Settings");
    private final JMenuItem item7 = new JMenuItem(" Show profile");
    private final JMenuItem item5 = new JMenuItem(" Show users");
    private final JMenuItem item6 = new JMenuItem(" Manage users");

    private int sceneID;
    protected SceneManager manager;
    private JFrame mainFrame;

    private final ArrayList<IPainter> listOfSprites = new ArrayList<>();
    private final ArrayList<Object> listOfObjects = new ArrayList<>();

    /**
     * Constructor for adding menubars to each scene, and updating it (removing/hiding elements based on the user role).
     *
     * @param sceneID What should be the id of the scene
     * @param manager What will manage these scenes
     */
    public Scene(int sceneID, SceneManager manager){
        this.setSceneID(sceneID);
        this.setManager(manager);

        //-----MENU BAR SETTINGS-----//
        item1.addActionListener(e -> manager.activateScene(1));
        menu1.add(item1);
        item2.addActionListener(e -> manager.activateScene(2));
        menu1.add(item2);
        menu1.addSeparator();
        item3.addActionListener(e -> mainFrame.dispose());
        menu1.add(item3);
        item4.addActionListener(e -> manager.activateScene(7));
        menu2.add(item4);
        item7.addActionListener(e -> manager.activateScene(8));
        menu2.add(item7);
        item8.addActionListener(e -> manager.activateScene(2));
        menu4.add(item8);

        menuBar.add(menu1);
        menuBar.add(menu4);
        menuBar.add(menu2);

        //admin bar setup
        item5.addActionListener(e -> manager.activateScene(5));
        menu3.add(item5);
        item6.addActionListener(e -> manager.activateScene(6));
        menu3.add(item6);
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public void setManager(SceneManager manager) {this.manager = manager;}

    public SceneManager getManager(){return this.manager;}

    public ArrayList<IPainter> getListOfSprites() {
        return listOfSprites;
    }

    public ArrayList<Object> getListOfObjects() {
        return listOfObjects;
    }

    public void setFrame(JFrame frame){this.mainFrame = frame;}

    protected JFrame getMainFrame(){return this.mainFrame;}

    /**
     * Method for updating the menubar.
     * If the user an admin, extra elements will be shown.
     * However the app wont show any menubar in a few selected scenes.
     *
     * @param isActiveAdmin Boolean for returning weather the logged user is an admin
     */
    @SuppressWarnings("deprecation")
    public void updateMenuBar(boolean isActiveAdmin){
        if (isActiveAdmin)
            menuBar.add(menu3);
        else
            menuBar.remove(menu3);

        if (this.sceneID != 1 && mainFrame.getJMenuBar() == null)
            getMainFrame().setJMenuBar(menuBar);
        else if(!banList.contains(manager.activeScene().getSceneID()) && mainFrame.getJMenuBar() != null)
            getMainFrame().getJMenuBar().show(true);
        else if(banList.contains(this.sceneID) && mainFrame.getJMenuBar() != null)
            getMainFrame().getJMenuBar().show(false);
    }

    public int getSceneID(){return this.sceneID;}

    public void onInit(){}

    public void onActivate(){}

    public void setDefaultValues(){}
}
