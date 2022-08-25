package core;

import java.awt.image.BufferedImage;


@SuppressWarnings("SpellCheckingInspection")
public interface IPainter {
    int getCoordX();
    int getCoordY();
    int getID();
    void setCoordX(int xCoord);
    void setCoordY(int yCoord);

    BufferedImage getSprite();

    void setWidth(int width);

    default int getW() {
        return 0;
    }

    void setHeight(int height);

    default int getH() {
        return 0;
    }
}
