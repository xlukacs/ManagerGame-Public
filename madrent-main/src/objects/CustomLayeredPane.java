package objects;

import core.IPainter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CustomLayeredPane extends JLayeredPane {
    private ArrayList<IPainter> localSprites = new ArrayList<>();

    /**
     * Override paintComponent method, so we can also draw our sprites amongst the default objects.
     *
     * @param g Graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        localSprites.forEach(sprite -> g.drawImage(sprite.getSprite(), sprite.getCoordX(), sprite.getCoordY(), sprite.getW(), sprite.getH(), null));
    }

    public void updateSpriteList(ArrayList<IPainter> newSpriteList){
        this.localSprites = newSpriteList;
    }
}
