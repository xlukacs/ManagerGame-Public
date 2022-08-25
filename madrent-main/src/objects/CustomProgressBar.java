package objects;

import core.ICustom;
import roles.Customer;

import javax.swing.*;

public class CustomProgressBar extends JProgressBar implements ICustom {
    private Customer forCustomer;

    public CustomProgressBar(int min, int max, Customer forCustomer) {
        super(min, max);
        this.forCustomer = forCustomer;
    }

    public CustomProgressBar(int min, int max){
        super(min, max);
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
