package core;

import java.util.ArrayList;

/**
 * Make sure, that every scene has the getSceneID, setDefaultObjects, onActive methods for the scene manager.
 * Also have access methods to ArrayLists for the scenes elements and sprites.
 */
public interface IScene {
    ArrayList<IPainter> getListOfSprites();
    ArrayList<Object> getListOfObjects();

    default void clearObjects(){
        getListOfObjects().clear();
    }

    int getSceneID();
    default void setDefaultValues(){}
    default void onActivate(){}
}
