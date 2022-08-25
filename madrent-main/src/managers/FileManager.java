package managers;

import core.FileErrorException;
import core.IPerson;
import core.User;
import roles.Admin;
import roles.Customer;
import roles.Guest;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * This class has all the methods, which works with serialising or changing user data, which needs to be stored permanently.
 * Everything stored here will be available on the next startup.
 */
public class FileManager {
    private String json;

    public FileManager() throws IOException {
        init();
    }

    public void init() throws IOException {
        String uri = System.getProperty("user.dir") + "/src/resources/userData.txt";
        json = readAllToString(uri);
    }


    /**
     * Method for deserializing user data.
     *
     * @return ArrayList<User>
     * @throws IOException
     */
    public ArrayList<User> getUserDataByLine() throws IOException {
        ArrayList<User> lines = new ArrayList<>();

        try {
            File f = new File(System.getProperty("user.dir") + "\\src\\resources\\users.ser");
            if(!f.exists())
                System.out.println("File is not there...");

            FileInputStream file = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(file);

            lines = (ArrayList)in.readObject();

            in.close();
            file.close();
        }catch (FileNotFoundException e){
            //e.printStackTrace();
            throw new FileNotFoundException("Failed to read from users data.");
        }catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println(System.getProperty("user.dir") + "\\src\\resources\\users.ser - not found.");
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        }

        return lines;
    }

    public String readAllToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public Image readImage(String imageName){
        String url = System.getProperty("user.dir") + "/src/resources/" + imageName;
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    /**
     * What should happen to the user, when serialising.
     */
    public enum Action{
        DELETE,
        ADD,
        RETAIN
    }

    /**
     * Method for adding users to the existing ArrayList and then serializing it.
     *
     * @param user New User to be added
     * @param list Existing ArrayList
     * @param operation DELETE, ADD, RETAIN => Delete user from list, add user to list, or don't change the list
     * @return boolean Was writing successful
     * @throws IOException
     * @throws FileErrorException
     */
    public boolean writeToFile(User user, ArrayList<User> list, Action operation) throws IOException, FileErrorException {
        ArrayList<User> newList;

        //Decide if we want to use the provided list or read from file
        if (list == null)
            newList = getUserDataByLine();
        else
            newList = list;

        //Decide if we want to add or remove from the list
        if (operation == Action.ADD)
            newList.add(user);
        else if(operation == Action.DELETE)
            newList = removeUserFromList(user, newList);


        //Do the serialisation
        try {
            FileOutputStream file = new FileOutputStream(System.getProperty("user.dir") + "/src/resources/users.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(newList);
            out.flush();

            out.close();
            file.close();

            return true;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            throw new FileErrorException("File error occurred - probably the file was not found.");
        }catch (IOException ex) {
            System.out.println("IOException is caught");
            throw new FileErrorException("File error occurred - probably the application could not open the file.");
        }
    }

    /**
     * Generic method for creating new Users
     *
     * @param username Username of the new user
     * @param password Password of the new user
     * @param role What role should it have (Admin, Guest, Customer,... )
     * @param <E> Generic type
     * @return User return the newly created instance
     * @throws IOException
     */
    public <E> User createUser(String username, String password, E role) throws IOException {
        return new User(username, password, getUserDataByLine().size(), (IPerson) role);
    }

    /**
     * Generic method for creating roles for Users
     *
     * @param role What role should this method create
     * @param newSurname SurName of the role
     * @param newLastname LastName of the role
     * @param <E> Generic type
     * @return E Return the new role
     */
    public <E> E createRole(E role, String newSurname, String newLastname){
        E newRole;
        if (role instanceof Admin)
            newRole = (E) new Admin(newLastname, newSurname, 44);
        else if(role instanceof Customer)
            newRole = (E) new Customer(newLastname, newSurname, 44);
        else
            newRole = (E) new Guest(newLastname, newSurname, 44);

        return newRole;
    }

    /**
     * Method for removing Users from an existing ArrayList.
     * The method iterates through the ArrayList and where the object at the iterator matches the searched object, delete the object.
     * Return the ArrayList without the User.
     *
     * @param user What user should the method look for.
     * @param userList An already existing ArrayList of the Users
     * @return ArrayList<User> ArrayList without the searched user
     */
    public ArrayList<User> removeUserFromList(User user, ArrayList<User> userList){
        userList.removeIf(userAtIterator -> userAtIterator == user);

        return userList;
    }

    /**
     * Method for adding an User to the internal ArrayList and also serialising this change.
     *
     * @param data User data
     * @param list Existing internal ArrayList
     * @param operation What change should the method make
     * @param <E> Generic type
     * @throws IOException
     * @throws FileErrorException
     */
    public <E> void writeListToFile(E data, ArrayList<E> list, Action operation) throws IOException, FileErrorException {
        if (data instanceof User)
            writeToFile((User)data, (ArrayList<User>) list, operation);
        else
            System.out.println("Type not yet implemented.");
    }
}
