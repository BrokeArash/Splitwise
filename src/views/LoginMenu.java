package views;
/*
Explanation:
- This is a view class for the login menu.
- This class should use to check inputs and print outputs for the login menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.LoginMenuController;
import models.Result;
import models.enums.LoginMenuCommands;
import models.App;
import models.enums.Menu;

import com.ahmz.test.tester.Scanner;  // My Scanner import is necessary
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        LoginMenuCommands matchedCommand = null;
        for (LoginMenuCommands command : LoginMenuCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher != null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }

        if (matchedCommand == null) {
            System.out.println("invalid command!");
        } else {
            switch (matchedCommand) {
                case Login:
                    Matcher loginMatcher = LoginMenuCommands.Login.getMatcher(input);
                    String username = loginMatcher.group("username");
                    String password = loginMatcher.group("password");
                    Result result = controller.login(username, password);
                    if (result.isTrue()) {
                        System.out.println(result);
                        App.setLogSuccessful(true);
                    } else {
                        System.out.println(result);
                        App.setLogSuccessful(false);
                    }
                    break;
                case ForgetPass:
                    Matcher forgetMatcher = LoginMenuCommands.ForgetPass.getMatcher(input);
                    username = forgetMatcher.group("username");
                    String email = forgetMatcher.group("email");
                    result = controller.forgetPass(username, email);
                    System.out.println(result);
                    break;
                case GoToSignUp:
                    System.out.println(controller.GoToSignUp());
                    break;
                case Exit:
                    System.exit(0);
            }
        }
    }
}
