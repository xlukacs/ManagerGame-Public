package core;

import java.io.Serializable;

/**
 * The basic data schema for all users.
 */
public abstract class Person implements IPerson, Serializable {
    protected Name name = new Name();
    protected int age;

    /**
     * Constructor for setting the basic information about the user.
     *
     * @param firstName
     * @param surName
     * @param age
     */
    public Person(String firstName, String surName, int age){
        this.name.setFirstName(firstName);
        this.name.setSurName(surName);
    }

    public void isWorker(){
        System.out.println("I am not a worker of the company.");
    }

    public String getFullName(){
        return this.name.getFirstName() + " " + this.name.getSurName();
    }

    public Name getName(){
        return this.name;
    }
}
