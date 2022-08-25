package managers;

import core.FileErrorException;
import core.Settings;
import core.User;
import roles.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class handles all user actions and changes GUI elements or file systems.
 */
public class UserManager {
    //TODO this is temporary, move these and update these workers somewhere
    private Architect theArchitectItself = new Architect("Jane", "Doe", 44);
    private ArrayList<ConstructionManager> constManagers = new ArrayList<>();
    private ConstructionManager aManager = new ConstructionManager("John", "Doe", 55);
    private ArrayList<ConstructionWorker> constWorkers = new ArrayList<>();
    private ConstructionWorker aWorker = new ConstructionWorker("Jim", "Doe", 66);
    private ConstructionWorker bWorker = new ConstructionWorker("Jony", "Doe", 77);

    private ArrayList<User> users;
    private final FileManager fileManager;
    private User activeUser;

    /**
     * Add default workers of the company, set internal copy of the Users list.
     * Set filemanager, to handle user actions permanently.
     *
     * @param fileManager What fileManager instance should this manager use
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public UserManager(FileManager fileManager) throws IOException, ClassNotFoundException {
        //TODO move these also
        getConstManagers().add(aManager);
        getConstWorkers().add(aWorker);
        bWorker.increaseExperience();
        bWorker.increaseExperience();
        bWorker.increaseExperience();
        bWorker.increaseExperience();
        bWorker.increaseExperience();
        getConstWorkers().add(bWorker);

        this.fileManager = fileManager;
        this.setUsers(fileManager.getUserDataByLine());
    }

    /**
     * Method for checking out the users credentials and trying to log in.
     * If it was a success, than set it as the active User.
     *
     * @param username The username of the User
     * @param password The password of the User hashed
     * @return boolean Was the login successful or not
     */
    public boolean tryLogin(String username, String password){
        for (User user : getUsers())
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                setActiveUser(user);
                return true;
            }

        return false;
    }

    /**
     * Method for registering the user and serialising this change.
     *
     * @param username Username to be stored
     * @param password Password to be stored
     * @return boolean Was this operation successful or not
     * @throws IOException
     * @throws FileErrorException
     * @throws ClassNotFoundException
     */
    public boolean tryRegister(String username, String password) throws IOException, FileErrorException, ClassNotFoundException {
        if(Settings.defaultRank == Settings.RANK.Guest) {
            if (fileManager.writeToFile(fileManager.createUser(username, password, new Guest("John", "Doe", 44)), getUsers(), FileManager.Action.ADD)) {
                this.setUsers(fileManager.getUserDataByLine());
                getUsers().forEach(user -> System.out.println(user.getUsername()));

                return true;
            } else
                return false;
        }
        if(Settings.defaultRank == Settings.RANK.Customer) {
            if (fileManager.writeToFile(fileManager.createUser(username, password, new Customer("John", "Doe", 44)), getUsers(), FileManager.Action.ADD)) {
                this.setUsers(fileManager.getUserDataByLine());
                getUsers().forEach(user -> System.out.println(user.getUsername()));

                return true;
            } else
                return false;
        }
        if(Settings.defaultRank == Settings.RANK.Admin) {
            if (fileManager.writeToFile(fileManager.createUser(username, password, new Admin("John", "Doe", 44)), getUsers(), FileManager.Action.ADD)) {
                this.setUsers(fileManager.getUserDataByLine());
                getUsers().forEach(user -> System.out.println(user.getUsername()));

                return true;
            } else
                return false;
        }


        return false;
    }

    /**
     * Method for returning weather the active user is an admin or not.
     *
     * @return boolean Return true if the active user is an admin, return false if the active user is anything else
     */
    public boolean isActiveAdmin(){
        try {
            if(getActiveUser().returnWhoIAm() instanceof Admin)
                return true;
        }catch (NullPointerException ne){
            return false;
        }

        return false;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Architect getTheArchitectItself() {
        return theArchitectItself;
    }

    public ArrayList<ConstructionManager> getConstManagers() {
        return constManagers;
    }

    public ArrayList<ConstructionWorker> getConstWorkers() {
        return constWorkers;
    }

    public FileManager getFileManager(){ return this.fileManager;}

    /**
     * Method for changing some details about the user.
     *
     * @param activeUser The current user, which requested the change
     * @param changedUser The new user, reflecting the changes
     * @return User Return the changed users, and set it as the active User.
     * @throws IOException
     * @throws FileErrorException
     */
    public User changeUserData(User activeUser, User changedUser) throws IOException, FileErrorException {
        setUsers(getFileManager().removeUserFromList(activeUser, getUsers()));
        getUsers().add(changedUser);
        getFileManager().writeToFile(null, getUsers(), FileManager.Action.RETAIN);

        return changedUser;
    }
}
