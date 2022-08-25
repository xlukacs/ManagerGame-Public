package objects;

import core.IPainter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class is the same as the object, but it has a texture in it, which will be renderen on the exact place, where we define it.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Sprite extends Object implements IPainter{
    private BufferedImage texture;

    /**
     * Set default values and load in the texture from the resources.
     *
     * @param xCoord
     * @param yCoord
     * @param width
     * @param height
     * @param textureName In string, how the app should look for the texture in the resources folder
     * @throws IOException
     */
    public Sprite(int xCoord, int yCoord, int width, int height, String textureName) throws IOException {
        super(xCoord, yCoord, width, height);
        loadImage(textureName);
    }

    /**
     * Load in the image from the resources.
     *
     * @param textureName
     * @throws IOException
     */
    public void loadImage(String textureName) throws IOException {
        texture = ImageIO.read(getClass().getResource("/resources/" + textureName));
    }

    /**
     * Return a the texture as a buffered image, because we need it quickly.
     * @return BufferedImage
     */
    public BufferedImage getSprite(){
        return texture;
    }

    @Override
    public int getW() {
        return getWidth();
    }

    @Override
    public int getH() {
        return getHeight();
    }
}
