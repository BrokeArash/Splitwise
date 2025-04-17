package views;

/*
Explanation:
- This is a view class for the SignUpMenu.
- This class should use to check inputs and print outputs for the SignUpMenu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.SignUpMenuController;
import models.App;
import models.Result;
import models.enums.DashboardCommands;
import models.enums.SignUpMenuCommands;

import com.ahmz.test.tester.Scanner;  // My Scanner import is necessary
import java.util.regex.Matcher;

public class SignUpMenu implements AppMenu {
    private final SignUpMenuController controller = new SignUpMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        SignUpMenuCommands matchedCommand = null;
        for (SignUpMenuCommands command : SignUpMenuCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher!= null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }
        if (matchedCommand == null ||
        matchedCommand == SignUpMenuCommands.CheckEmail ||
        matchedCommand == SignUpMenuCommands.CheckPass ||
        matchedCommand == SignUpMenuCommands.CheckUser ||
        matchedCommand == SignUpMenuCommands.CheckName) {
            System.out.println("invalid command!");
        }
        else {
            switch (matchedCommand) {
                case Exit:
                    System.exit(0);
                case GoToLogin:
                    System.out.println(controller.goToLogin());
                    break;
                case MainRegister:
                    Matcher registerMatcher = SignUpMenuCommands.MainRegister.getMatcher(input);
                    String username = registerMatcher.group("username");
                    String password = registerMatcher.group("password");
                    String email = registerMatcher.group("email");
                    String name = registerMatcher.group("name");
                    Result result = controller.register(username, password, email, name);
                    System.out.println(result);
                    break;
                default:
                    break;
            }
        }
    }
}
