package roles;

import core.Construction;
import core.Person;

import javax.swing.*;
import java.io.Serializable;

/**
 * Plain new freshly registered user, who has not ordered a construction yet.
 * Can only order a construction.
 */
public class Guest extends Person implements Serializable {
    public Guest(String firstName, String surName, int age) {
        super(firstName, surName, age);
    }

    /**
     * A new guest for their first time requesting a construction will only show a popup window, saying that it has confirmed the construction.
     * After this point it will get converted to a customer, in which it will sign the contract.
     * @param construction The construction which the guest has ordered.
     */
    public void confirmOrder(Construction construction){
        JOptionPane.showMessageDialog(null, "Order confirmed and accepted for construction: " + construction.getName());
    }
}
