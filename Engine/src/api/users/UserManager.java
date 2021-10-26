package api.users;

import api.DTOUser;

import java.util.*;

/*
Adding and retrieving users is synchronized and in that manner - these actions are thread safe
Note that asking if a user exists (isUserExists) does not participate in the synchronization and it is the responsibility
of the user of this class to handle the synchronization of isUserExists with other methods here on it's own
 */
public class UserManager {

    private final Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
    }

    public synchronized void addUser(String username, String accountType)
    {
        if(accountType.equals("Customer")){
            users.put(username.toLowerCase(), new Buyer(username));
        }
        else{
            users.put(username.toLowerCase(), new Owner(username));
        }
    }

    public synchronized void removeUser(String username) {
        users.remove(username);
    }

    public synchronized List<DTOUser> getUsersData() {
        List<DTOUser> users = new ArrayList<>();

        for (User user: this.users.values()){
            users.add(new DTOUser(user));
        }

        return users;
    }
    public synchronized DTOUser getUserData(String username) {
        return new DTOUser(this.users.get(username));
    }

    public boolean isUserExists(String username) {

        return this.users.containsKey(username.toLowerCase());
    }

    public synchronized User getUser(String userName){
        return this.users.get(userName.toLowerCase());
    }

}
