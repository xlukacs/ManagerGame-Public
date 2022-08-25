package core;

/**
 * Basic information schema for the user.
 * It contains authentication information and personal information besides the users role.
 */
public class User implements java.io.Serializable {
    private final String username;
    private final String password;
    private final int userId;
    private final IPerson role;

    public User(String username, String password, int userId, IPerson role) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId(){
        return userId;
    }

    public IPerson returnWhoIAm(){
        return this.role;
    }
}
