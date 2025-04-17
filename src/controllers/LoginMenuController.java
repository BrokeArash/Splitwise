package controllers;
/*
Explanation:
- This is a controller class for the login menu Controller.
- This class will be used to implement functions that do log in menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;

public class LoginMenuController {
    public Result login (String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "username doesn't exist!");
        } else if (!user.getPassword().equals(password)) {
            return new Result(false, "password is incorrect!");
        }
        App.setLoggedInUser(user);
        return new Result(true, "user logged in successfully.you are now in dashboard!");
    }

    public Result forgetPass (String username, String email) {
        User user = getUserByUsername(username);
        if (user == null) {
            return new Result(false, "username doesn't exist!");
        } else if (!user.getEmail().equals(email)) {
            return new Result(false, "email doesn't match!");
        }
        return new Result(true, "password : " + user.getPassword());
    }

    public Result GoToSignUp () {
        App.setGoToSignUpRequested(true);
        App.setLoggedInUser(null);
        return new Result(true, "you are now in signup menu!");
    }


    private User getUserByUsername(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
