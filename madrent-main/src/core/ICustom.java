package core;

import roles.Customer;

public interface ICustom {
    default Customer getForCustomer() {
        return null;
    }

    void setForCustomer(Customer forCustomer);
}
