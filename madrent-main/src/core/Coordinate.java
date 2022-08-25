package core;

@SuppressWarnings("SpellCheckingInspection")
public class Coordinate {
    private int posX;
    private int posY;

    @SuppressWarnings("unused")
    public Coordinate(int xCoord, int yCoord){
        this.setPosX(xCoord);
        this.setPosY(yCoord);
    }

    public Coordinate(){}

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
