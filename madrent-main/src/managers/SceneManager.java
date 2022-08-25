package managers;

import core.IScene;
import core.Scene;
import core.Settings;
import objects.CustomLayeredPane;
import scenes.Customer.ViewConstruction;
import scenes.LoginScene;
import scenes.MenuScene;
import scenes.RegisterScene;
import scenes.admin.ListUsers;
import scenes.admin.ManageUsers;
import scenes.guest.OrderConstruction;
import scenes.profile.ProfileSettings;
import scenes.profile.ShowProfile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This is the GUI framework. This class manages and changes scenes dynamically, based on user actions.
 */
@SuppressWarnings("FieldCanBeLocal")
public class SceneManager {
    private final boolean hasBeenInitialised;

    private final ArrayList<Scene> scenes = new ArrayList<>();
    private int activeSceneID = 0;
    private CustomLayeredPane pane;
    private UserManager userManager;

    private final LoginScene loginScene = new LoginScene(1, this);
    private final MenuScene  menuScene = new MenuScene (2, this);
    private final LoginScene adminScene = new LoginScene(3, this);
    private final RegisterScene registerScene = new RegisterScene(4, this);
    private final ListUsers listUsersScene = new ListUsers(5, this);
    private final ManageUsers manageUsersScene = new ManageUsers(6, this);
    private final ProfileSettings profileSettingsScene = new ProfileSettings(7, this);
    private final ShowProfile showProfileScene = new ShowProfile(8, this);
    private final OrderConstruction orderConstructionScene = new OrderConstruction(9, this);
    private final ViewConstruction viewConstructionScene = new ViewConstruction(10, this);

    /**
     * Constructor setting up the scenes. Adding each of them to the known list and initialising the drawing pane.
     *
     * @param pane The pane which it will use to draw elements
     * @param userManager Set the managers, which will handle the user actions
     * @throws IOException
     */
    public SceneManager(CustomLayeredPane pane, UserManager userManager) throws IOException {
        scenes.add(loginScene);
        scenes.add(menuScene);
        scenes.add(adminScene);
        scenes.add(registerScene);
        scenes.add(listUsersScene);
        scenes.add(manageUsersScene);
        scenes.add(profileSettingsScene);
        scenes.add(showProfileScene);
        scenes.add(orderConstructionScene);
        scenes.add(viewConstructionScene);
        activeSceneID = 1; //default is the login screen

        init(pane, userManager);
        hasBeenInitialised = true;
    }

    /**
     * Add mainFrame to each of the scenes
     *
     * @param frame Main frame
     */
    public void setFrame(JFrame frame){
        scenes.forEach(scene -> scene.setFrame(frame));
    }

    /**
     * Method for returning the activeScene.
     *
     * @return IScene Return the current active scene
     */
    public IScene activeScene(){
        for (Scene scene : scenes) {
            if (scene.getSceneID() == activeSceneID)
                return scene;
        }
        return null;
    }

    /**
     * Method for initialising each of the scenes, activating the default scene.
     *
     * @param pane
     * @param userManager
     */
    public void init(CustomLayeredPane pane, UserManager userManager){
        this.pane = pane;
        this.userManager = userManager;

        pane.setSize(Settings.windowWidth, Settings.windowHeight);
        activateScene(activeSceneID);

        scenes.forEach(Scene::onInit);
    }

    public UserManager getLoginManager(){
        return this.userManager;
    }

    /**
     * Method for activating a new scene. On trigger, remove all elements from the main pane.
     * Run scene functions  - onActivate = run scene code before activating
     *                      - setDefaultValues = run scene code after elements have been transfered to pane
     *                      - updateMenuBar = detect if logged in user is an admin, and change the menubar accordingly
     *
     * Than add new sprites and object to the pane from the scene.
     * Finally update the frame (revalidate, repaint).
     *
     * @param sceneID which scene should be displayed
     */
    public void activateScene(int sceneID){
        this.activeSceneID = sceneID;

        //removeAnd ClearEverything
        pane.removeAll();

        //add new sprites and components
        activeScene().onActivate();
        pane.updateSpriteList(activeScene().getListOfSprites());
        activeScene().getListOfObjects().forEach(elem -> pane.add((Component)elem, JLayeredPane.DEFAULT_LAYER));
        activeScene().setDefaultValues();

        //update the graphics
        updateScene();

        //update menu(add or remove) if necessary
        if (hasBeenInitialised)
            scenes.forEach(scene -> scene.updateMenuBar(userManager.isActiveAdmin()));
    }

    /**
     * Method for returning a scene, which has the requested sceneID stored.
     *
     * @param sceneID Which scene should be returned.
     * @return Scene Found scene or null.
     */
    public Scene getScene(int sceneID){
        for (Scene scene : scenes)
            if (scene.getSceneID() == sceneID)
                return scene;

        return null;
    }

    /**
     * Method for updating the GUI
     */
    public void updateScene(){
        pane.revalidate();
        pane.repaint();
    }
}
