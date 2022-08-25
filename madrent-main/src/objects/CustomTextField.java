package objects;

import core.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CustomTextField extends JTextField {
    public CustomTextField(String defaultValue){
        this();
        setText(defaultValue);
    }

    public CustomTextField(){
        setBorder(new EmptyBorder(0,5,0,5));
    }

    public void setBoundsAndCenter(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if(getParent() != null)
            setLocation(getParent().getWidth()  / 2 - getWidth() / 2, getY());
        else
            setLocation(Settings.windowWidth  / 2 - getWidth() / 2, getY());
    }

    public void setBoundsAndCenter(int x, int y, int width, int height, int parentWidth){
        setBoundsAndCenter(x,y,width,height);
        setLocation(parentWidth  / 2 - getWidth() / 2, getY());
    }
}
