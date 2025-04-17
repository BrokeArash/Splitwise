package controllers;

import models.App;
import models.Group;
import models.Result;
import models.User;
import models.enums.Currency;
import models.enums.DashboardCommands;
import models.enums.GroupType;

import java.util.*;
import java.util.regex.Matcher;

/*
Explanation:
- This is a controller class for the dashboard Controller.
- This class will be used to implement functions that do dashboard operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */
public class DashboardController {
    public Result createGroup (String groupName, String type) {
        Matcher typeMatcher = GroupType.GroupType.getMatcher(type);
        if (groupName.length() < 4 || groupName.length() > 30) {
            return new Result(false, "group name format is invalid!");
        } else if (typeMatcher == null) {
            return new Result(false, "group type is invalid!");
        }
        Group newGroup = new Group(groupName, App.getLoggedInUser(), type);
        App.getLoggedInUser().groups.add(newGroup);
        App.groups.add(newGroup);
        newGroup.members.add(App.getLoggedInUser());
        return new Result(true, "group created successfully!");
    }

    public void showGroups() {
        if (App.getLoggedInUser().groups.isEmpty()) {
            System.out.println();
            return;
        }
        for (Group group : App.getLoggedInUser().groups) {
            System.out.println("group name : "+group.name);
            System.out.println("id : "+group.id);
            System.out.println("type : "+group.type);
            System.out.println("creator : "+group.creator.getName());
            System.out.println("members : ");
            for (User member : group.members) {
                System.out.println(member.getName());
            }
            System.out.println("--------------------");
        }
    }

    public Result addUser(String username, String email, int id) {
        User user = getUserByUsername(username);
        Group group = getGroupByID(id);
        if (user == null) {
            return new Result(false, "user not found!");
        } else if (group == null) {
            return new Result(false, "group not found!");
        } else if (isUserInGroup(username, id)) {
            return new Result(false, "user already in the group!");
        } else if (!user.getEmail().equals(email)) {
            return new Result(false, "the email provided does not match the username!");
        } else if (!group.creator.equals(App.getLoggedInUser())) {
            return new Result(false, "only the group creator can add users!");
        } else {
            group.members.add(user);
            user.groups.add(group);
            return new Result(true, "user added to the group successfully!");
        }


    }

    public Result addExpense(int id, String isEqual, long total, int numUser, ArrayList<String> namesTemp, ArrayList<Long> moneyTemp, long sum, boolean isValidExpense) {
        Group group = getUserGroupByID(id);
        User user = App.getLoggedInUser();
        long each = (total/ Currency.Currency.getValue(user.getCurrency())) / numUser;

        boolean isCorrect = true;
        if (isEqual.equals("equally")) {
            for (int i = 0; i < numUser; i++) {
                if(!isUserInGroup(namesTemp.get(i), id)) {
                    System.out.println(namesTemp.get(i).trim()+" not in group!");
                    isCorrect = false;
                }
            }
            if (!isCorrect) {
                return new Result(false, "");
            } else if (!isValidExpense || total == 0) {
                return new Result(false, "expense format is invalid!");
            } else {
                for (int i = 0; i < numUser; i++) {
                    user.addExpense(group, getUserByUsername(namesTemp.get(i)), each);
                    getUserByUsername(namesTemp.get(i)).addExpense(group, user, -each);
                }
            }
            namesTemp.clear();
        } else {
            for (int i = 0; i < numUser; i++) {
                if(!isUserInGroup(namesTemp.get(i), id)) {
                    System.out.println(namesTemp.get(i)+" not in group!");
                    isCorrect = false;
                }
            }
            if (!isCorrect) {
                return new Result(false, "");
            } else if (!isValidExpense || total == 0) { //TODO: check if it is correct
                return new Result(false, "expense format is invalid!");
            } else if (sum != total) {
                return new Result(false, "the sum of individual costs does not equal the total cost!");
            } else {
                for (int i = 0; i < numUser; i++) {
                    user.addExpense(group, getUserByUsername(namesTemp.get(i)), moneyTemp.get(i));
                    getUserByUsername(namesTemp.get(i)).addExpense(group, user, -moneyTemp.get(i));
                }
            }
            namesTemp.clear();
            moneyTemp.clear();

        }
        return new Result(true, "expense added successfully!");
    }

    public Result showBalance(String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "user not found!");
        }

        User mainUser = App.getLoggedInUser();
        String groupNames = "";
        long netBalance = 0;
        for (Group group : mainUser.groups) {
            if (group.members.contains(user)) {
                netBalance += mainUser.getExpense(group, user);
                groupNames += (group.name + ", ");
            }
        }
        groupNames = groupNames.substring(0, groupNames.length()-2) + "!";
        String message;
        if (netBalance > 0) {
            message = username + " owes you " + netBalance*Currency.Currency.getValue(mainUser.getCurrency()) + " " + mainUser.getCurrency() + " in " + groupNames;
        } else if (netBalance < 0) {
            message = "you owe " + username + " " + (-netBalance)*Currency.Currency.getValue(mainUser.getCurrency()) + " " + mainUser.getCurrency() + " in " + groupNames;
        } else {
            message = "you are settled with " + username;
        }

        return new Result(true, message);
    }

    public Result settleUp(String username, long inputMoney, boolean isValid) {
        User mainUser = App.getLoggedInUser();
        User user = getUserByUsername(username);
        Group lastGroup = null;
        long newPrice = inputMoney/Currency.Currency.getValue(mainUser.getCurrency());
        if (user == null) {
            return new Result(false, "user not found!");
        } else if (!isValid) {
            return new Result(false, "input money format is invalid!");
        }
        int numGroups = 0;
        for (Group group : mainUser.groups) {
            if (group.members.contains(user)) {
                numGroups++;
            }
        }
        for (Group group : mainUser.groups) {
            if (group.members.contains(user)) {
                if (numGroups > 1) {
                    long balance = mainUser.getExpense(group, user);
                    newPrice += balance;
                    mainUser.settleExpense(group, user);
                    user.settleExpense(group, mainUser);
                }
                else {
                    lastGroup = group;
                    long balance1 = mainUser.getExpense(group, user);
                    newPrice += balance1;
                    mainUser.settleExpense(group, user);
                    user.settleExpense(group, mainUser);


                }
                numGroups--;
            }
        }

        mainUser.addExpense(lastGroup, user, newPrice);
        user.addExpense(lastGroup, mainUser, (-newPrice));
        newPrice *= Currency.Currency.getValue(mainUser.getCurrency());

        if (newPrice > 0) {
            return new Result(true, username+" owes you "+ newPrice + " " + mainUser.getCurrency() + " now!");
        } else if (newPrice < 0) {
            return new Result(true, "you owe "+ username + " " + -newPrice + " " + mainUser.getCurrency() + " now!");
        } else {
            return new Result(true, "you are settled with " + username + " now!");
        }
    }

    public Result goToProfile() {
        App.setGoToProfileRequested(true);
        return new Result(true, "you are now in profile menu!");
    }

    public Result logOut() {
        App.setLogOutRequested(true);
        return new Result(true, "user logged out successfully.you are now in login menu!");
    }

    private User getUserByUsername(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private Group getGroupByID(int id) {
        for (Group group : App.groups) {
            if(group.id == id) {
                return group;
            }
        }
        return null;
    }
    private Group getUserGroupByID(int id) {
        for (Group group : App.getLoggedInUser().groups) {
            if(group.id == id) {
                return group;
            }
        }
        return null;
    }

    private boolean isUserInGroup (String username, int id) {
        User mainUser = getUserByUsername(username);
        for (User user : getGroupByID(id).members) {
            if (user.equals(mainUser)){
                return true;
            }
        }
        return false;
    }
}