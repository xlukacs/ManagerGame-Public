package objects;

import core.ICustom;
import core.Settings;
import roles.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomLabel extends JLabel implements ICustom {
    private Customer forCustomer;

    public CustomLabel(String text, Customer forCustomer){
        this(text);
        this.setForCustomer(forCustomer);
    }

    public CustomLabel(String text) {
        super(text);
    }

    /**
     * Option for adding space around the label with background.
     *
     * @param text Text to be displayed
     * @param padding Space around the label
     */
    public CustomLabel(String text, Insets padding){
        super(text);
        setBorder(new EmptyBorder(padding));
    }

    public void centerThis(){
        setLocation(Settings.windowWidth/2 - getWidth() / 2, getY());
    }

    @Override
    public Customer getForCustomer() {
        return forCustomer;
    }

    @Override
    public void setForCustomer(Customer forCustomer) {
        this.forCustomer = forCustomer;
    }
}
