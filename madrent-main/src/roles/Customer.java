package roles;

import core.Construction;

import java.util.ArrayList;

/**
 * Has ordered a construction at least once. Can see the progress of the construction which have been ordered by it.
 */
public class Customer extends Guest{
    private ArrayList<Construction> constructions = new ArrayList<>();

    public Customer(String firstName, String surName, int age) {
        super(firstName, surName, age);
    }

    /**
     * When a construction has been created, the customer needs to confirm it(giving the permissions and signing the contract).
     *
     * @param construction The ordered construction
     */
    @Override
    public void confirmOrder(Construction construction) {
        super.confirmOrder(construction);
        construction.setCustomer(this);
        construction.confirmReadyFromCustomer();

        try {
            getConstructions().add(construction);
        }catch (NullPointerException ne){
            System.out.println("Construction did not get assigned to the customer.");
        }
    }

    private ArrayList<Construction> getConstructions() {
        return constructions;
    }
}
