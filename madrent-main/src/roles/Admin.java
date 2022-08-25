package roles;

import core.Person;

/**
 * Manages all of the users records, including the admins.
 */
public class Admin extends Person{
    public Admin(String firstName, String surName, int age) {
        super(firstName, surName, age);
    }

    public void changeRecord(){

    }
}
