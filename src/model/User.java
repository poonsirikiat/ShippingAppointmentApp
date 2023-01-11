package model;
/**
 * Supplied class User.java
 */
public class User {
    private int userID;
    private String userName;
    private String userPassword;

    public User(int userID, String userName, String userPassword){
        this.userID = userID;
        this.userName= userName;
        this.userPassword = userPassword;
    }

    /**
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID the ID to set
     */
    public void setUserID(int userID){
        this.userID = userID;
    }

    /**
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return the user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     *
     * @param userPassword the password to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
