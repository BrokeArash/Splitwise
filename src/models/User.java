package models;

/*
Explanation:
- User is definitely an object in our app.
- put the information that you need to store about the user here.
- you can put some functions here to manage the user data too.
 */

import java.util.ArrayList;
import java.util.HashMap;

public class User {



    private String username;
    private String password;
    private String email;
    private final String name;
    private String currency;
    public ArrayList<Group> groups = new ArrayList<>();
    private HashMap<Group, HashMap<User, Long>> groupExpenses = new HashMap<>();



    public User(String username, String email, String name, String password) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.currency = "GTC";
    }





    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void changeCurrency(String currency) {
        this.currency = currency;
    }
    public String getCurrency() {
        return currency;
    }

    public void changePassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public void changeUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void addExpense(Group group, User otherUser, long amount) {
        groupExpenses.putIfAbsent(group, new HashMap<>());

        HashMap<User, Long> expensesInGroup = groupExpenses.get(group);

        if (expensesInGroup.containsKey(otherUser)) {
            expensesInGroup.compute(otherUser, (k, currentAmount) -> currentAmount + amount);
        } else {
            expensesInGroup.put(otherUser, amount);
        }
    }
    public long getExpense(Group group, User otherUser) {
        if (groupExpenses.containsKey(group)) {
            return groupExpenses.get(group).getOrDefault(otherUser, 0L);
        }
        return 0L;
    }
    public void settleExpense(Group group, User otherUser) {
        if (groupExpenses.containsKey(group)) {
            groupExpenses.get(group).remove(otherUser);
        }
    }



}
