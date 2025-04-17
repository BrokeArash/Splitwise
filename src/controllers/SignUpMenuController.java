package controllers;
/*
Explanation:
- This is a controller class for the sign-up menu Controller.
- This class will be used to implement functions that do sign up menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.SignUpMenuCommands;
import java.util.regex.Matcher;

public class SignUpMenuController {

    public Result register(String username, String password, String email, String name) {
        Matcher checkUser = SignUpMenuCommands.CheckUser.getMatcher(username);
        Matcher checkPass = SignUpMenuCommands.CheckPass.getMatcher(password);
        Matcher checkEmail = SignUpMenuCommands.CheckEmail.getMatcher(email);
        Matcher checkName = SignUpMenuCommands.CheckName.getMatcher(name);
        if (checkUser == null || !checkUser.matches()) {
            return new Result(false, "username format is invalid!");
        } else if (!isUsernameUnique(username)) {
            return new Result(false, "this username is already taken!");
        } else if (checkPass == null || !checkPass.matches()) {
            return new Result(false, "password format is invalid!");
        } else if (checkEmail == null || !checkEmail.matches()) {
            return new Result(false, "email format is invalid!");
        } else if (checkName == null || !checkName.matches()) {
            return new Result(false, "name format is invalid!");
        } else {
            App.users.add(new User(username, email, name, password));
            App.setRegSuccessful(true);
            return new Result(true, "user registered successfully.you are now in login menu!");
        }
    }




//        if (username == null || !username.matches("^\\s*[A-Za-z][A-Za-z0-9._-]{3,9}`\\s*$")) {
//            return new Result(false, "username format is invalid!");
//        }
//
//        // Check if username is unique
//        else if (!isUsernameUnique(username)) {
//            return new Result(false, "this username is already taken!");
//        }
//
//        // Validate password
//        else if (password == null || !password.matches("^\\s*(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6,12}\\s*$")) {
//            return new Result(false, "password format is invalid!");
//        }
//
//        // Validate email
//        else if (email == null || !email.matches("^\\s*[A-Za-z][A-Za-z0-9._-]{3,9}@[A-Za-z]+([.-]?[A-Za-z]+)*\\.[A-Za-z]{2,6}\\s*$")) {
//            return new Result(false, "email format is invalid!");
//        }
//
//        // Validate name
//        else if (name == null || !name.matches("^\\s*[A-Za-z]+(?:-[A-Za-z]+)*\\s*$")) {
//            return new Result(false, "name format is invalid!");
//        }
//
//        // If all validations pass, register the user
//
//        App.users.add(new User(username, email, name, password));
//        return new Result(true, "user registered successfully.you are now in login menu!");


    private boolean isUsernameUnique(String username) {
        return getCustomerByUsername(username) == null;
    }

    private User getCustomerByUsername(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Result goToLogin() {
        App.setGoToLogInRequested(true);
        return new Result(true, "you are now in login menu!");
    }
}

