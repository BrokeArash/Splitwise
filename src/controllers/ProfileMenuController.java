package controllers;
/*
Explanation:
- This is a controller class for the profile menu Controller.
- This class will be used to implement functions that do profile menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Currency;
import models.enums.ProfileMenuCommands;
import java.util.regex.Matcher;

public class ProfileMenuController {
    public void showUserInfo() {
        User mainUser = App.getLoggedInUser();
        System.out.println("username : " + mainUser.getUsername());
        System.out.println("password : " + mainUser.getPassword());
        System.out.println("currency : " + mainUser.getCurrency());
        System.out.println("email : " + mainUser.getEmail());
        System.out.println("name : " + mainUser.getName());
    }

    private User getUserByUsername(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Result changeCurrency(String newCurrency) {
        Matcher currencyMatcher = Currency.Currency.getMatcher(newCurrency);
        if(currencyMatcher == null) {
            return new Result(false, "currency format is invalid!");
        }
        App.getLoggedInUser().changeCurrency(newCurrency);
        return new Result(true, "your currency changed to " + newCurrency +" successfully!");
    }

    public Result changeUsername(String newUsername) {
        Matcher usernameMatcher = ProfileMenuCommands.Username.getMatcher(newUsername);
        if(App.getLoggedInUser().getUsername().equals(newUsername)) {
            return new Result(false, "please enter a new username!");
        } else if (getUserByUsername(newUsername) != null) {
            return new Result(false, "this username is already taken!");
        } else if (usernameMatcher == null) {
            return new Result(false, "new username format is invalid!");
        }
        App.getLoggedInUser().changeUsername(newUsername);
        return new Result(true, "your username changed to " + newUsername + " successfully!");
    }

    public Result changePassword(String oldPassword, String newPassword) {
        Matcher passwordMatcher = ProfileMenuCommands.Password.getMatcher(newPassword);
        if (!App.getLoggedInUser().getPassword().equals(oldPassword)) {
            return new Result(false, "password incorrect!");
        } else if(App.getLoggedInUser().getPassword().equals(newPassword)) {
            return new Result(false, "please enter a new password!");
        } else if (passwordMatcher == null) {
            return new Result(false, "new password format is invalid!");
        }
        App.getLoggedInUser().changePassword(newPassword);
        return new Result(true, "your password changed successfully!");
    }

    public Result back() {
        App.setBackRequested(true);
        return new Result(true, "you are now in dashboard!");
    }


}
