package objects;

import core.Settings;

import javax.swing.*;

@SuppressWarnings("SpellCheckingInspection")
public class CustomButton extends JButton{

    public CustomButton(String text, Icon icon) {
        // Create the model
        setModel(new DefaultButtonModel());

        // initialize
        init(text, icon);
    }

    public CustomButton(int xCoord, int yCoord, int width, int height, String text){
        this(text, null);
        setBounds(xCoord, yCoord, width, height);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }

    public void centerThis(){
        this.setLocation(Settings.windowWidth / 2 - this.getWidth() / 2, this.getY());
    }
}
