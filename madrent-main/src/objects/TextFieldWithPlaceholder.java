package objects;

import core.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * JTextField with extra features, like automatic centering and having placeholder values.
 */
public class TextFieldWithPlaceholder extends JTextField {
    /**
     * Add placeholder effect to this input field.
     *
     * @param placeholder Text which will be displayed while the user didnt click into the input.
     */
    public TextFieldWithPlaceholder(String placeholder){
        this.setForeground(Color.GRAY);
        this.setText(placeholder);

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(placeholder);
                }
            }
        });
    }

    public void centerThis(){
        this.setLocation(Settings.windowWidth / 2 - getWidth() / 2, getY());
    }
}
