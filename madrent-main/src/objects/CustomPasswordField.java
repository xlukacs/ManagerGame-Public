package objects;

import core.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomPasswordField extends JPasswordField {
    /**
     * Constructor for adding placeholder like effect to password field.
     * If the user clicks in this are, then if nothing is in it from the user, then it will reset, and the user can type in the password.
     * The effect comes back, when there is nothing in it from the user.
     *
     * @param placeholder The placeholder text represented by * characters in the input field
     */
    public CustomPasswordField(String placeholder){
        this();
        String placeholder1 = placeholder;
        if (placeholder == null)
            placeholder1 = "Password";

        this.setForeground(Color.GRAY);
        this.setText(placeholder);

        String finalPlaceholder = placeholder1;
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(finalPlaceholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(finalPlaceholder);
                }
            }
        });
    }

    public CustomPasswordField(String text, boolean hasPlaceHolder){
        this(text);
        setText(text);
    }

    /**
     * Set padding for the password field if needed.
     */
    public CustomPasswordField(){
        setBorder(new EmptyBorder(0,5,0,5));
    }

    /**
     * Method for setting the size of the input field and centering it by its parents size.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void setBoundsAndCenter(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if(getParent() != null)
            setLocation(getParent().getWidth()  / 2 - getWidth() / 2, getY());
        else
            centerThis();
    }

    public void setBoundsAndCenter(int x, int y, int width, int height, int parentWidth){
        setBoundsAndCenter(x,y,width,height);
        setLocation(parentWidth  / 2 - getWidth() / 2, getY());
    }

    public void centerThis(){
        setLocation(Settings.windowWidth / 2 - getWidth() / 2, getY());
    }
}
