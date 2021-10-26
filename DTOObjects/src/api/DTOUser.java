package api;

import api.users.User;

public class DTOUser {
    private String name;
    private String accountType;
    private double balance;

    public DTOUser(User user){
        this.name = user.getName();
        this.accountType = user.getAccountType();
        this.balance = user.getBalance().getBalance();
    }
}
