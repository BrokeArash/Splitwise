package models;

import models.enums.Menu;

import java.util.ArrayList;

/*
Explanation:
- In out app, we need somewhere to keep our general data like list of users and list of groups and logged-in user etc.
- This class is the place for that.
- Put your general data here and use them in your code.
- you should put some functions here to manage your data too.
 */
public class App {
    public static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;
    private static Menu currentMenu = Menu.SignUp;
    private static boolean regSuccessful = false;
    private static boolean logSuccessful = false;
    private static boolean goToSignUpRequested = false;
    private static boolean goToLogInRequested = false;
    private static boolean GoToProfileRequested = false;
    private static boolean logOutRequested = false;
    private static boolean backRequested = false;
    public static ArrayList<Group> groups = new ArrayList<>();


    public static void setLogSuccessful(boolean logSuccessful) {
        App.logSuccessful = logSuccessful;
    }
    public static boolean isLogSuccessful() {
        return logSuccessful;
    }

    public static void setRegSuccessful(boolean regSuccessful) {
        App.regSuccessful = regSuccessful;
    }
    public static boolean isRegSuccessful() {
        return regSuccessful;
    }


    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static boolean isGoToSignUpRequested() {
        return goToSignUpRequested;
    }
    public static void setGoToSignUpRequested(boolean requested) {
        goToSignUpRequested = requested;
    }

    public static boolean isGoToProfileRequested() {
        return GoToProfileRequested;
    }

    public static void setGoToProfileRequested(boolean goToProfileRequested) {
        GoToProfileRequested = goToProfileRequested;
    }

    public static boolean isLogOutRequested() {
        return logOutRequested;
    }

    public static void setLogOutRequested(boolean logOutRequested) {
        App.logOutRequested = logOutRequested;
    }

    public static boolean isBackRequested() {
        return backRequested;
    }

    public static void setBackRequested(boolean backRequested) {
        App.backRequested = backRequested;
    }

    public static boolean isGoToLogInRequested() {
        return goToLogInRequested;
    }

    public static void setGoToLogInRequested(boolean goToLogInRequested) {
        App.goToLogInRequested = goToLogInRequested;
    }
}
