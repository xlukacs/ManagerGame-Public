package objects;

import core.Coordinate;
import core.Settings;

/**
 * Basic information, which every object drawn on the pane should have.
 * This class contains accessing methods for basic information like coordinates and dimensions of the object.
 */
@SuppressWarnings("SpellCheckingInspection")
public abstract class Object{
    private int width;
    private int height;
    protected int ID;
    protected Coordinate coord = new Coordinate();

    /**
     * Constructor for setting the default values of the object.
     *
     * @param xCoord
     * @param yCoord
     * @param width
     * @param height
     */
    public Object(int xCoord, int yCoord, int width, int height){
        this.setWidth(width);
        this.setHeight(height);
        this.coord.setPosX(xCoord);
        this.coord.setPosY(yCoord);
    }

    public int getID(){
        return this.ID;
    }

    public int getCoordX(){
        return this.coord.getPosX();
    }

    public void setCoordX(int xCoord){
        this.coord.setPosX(xCoord);
    }

    public int getCoordY(){
        return this.coord.getPosY();
    }

    public void setCoordY(int yCoord){
        this.coord.setPosY(yCoord);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Method for setting the object to the absolute center point on the x axis.
     */
    public void centerThis(){
        this.setCoordX(Settings.windowWidth / 2 - this.getWidth() / 2);
    }
}
