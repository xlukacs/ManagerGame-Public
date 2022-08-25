package core;

import java.io.Serializable;

/**
 * Accessing methods and variables to store the users full name.
 */
public class Name implements Serializable {
    private String surName;
    private String firstName;

    public String getSurName() {
        return surName;
    }

    public String getFirstName() {
        return firstName;
    }

    protected void setSurName(String surName) {
        this.surName = surName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
