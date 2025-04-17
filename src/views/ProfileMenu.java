package views;
/*
Explanation: 
- This is a view class for profile menu.
- This class should use to check inputs and print outputs for profile menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.DashboardController;
import controllers.ProfileMenuController;
import models.Result;
import models.enums.DashboardCommands;
import models.enums.ProfileMenuCommands;

import com.ahmz.test.tester.Scanner;  // My Scanner import is necessary
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    private final ProfileMenuController controller = new ProfileMenuController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        ProfileMenuCommands matchedCommand = null;
        for (ProfileMenuCommands command : ProfileMenuCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher != null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }

        if (matchedCommand == null ||
        matchedCommand == ProfileMenuCommands.Password ||
        matchedCommand == ProfileMenuCommands.Username) {
            System.out.println("invalid command!");
        } else {
            switch (matchedCommand) {

                case ShowUserInfo:
                    controller.showUserInfo();
                    break;
                case ChangeCurrency:
                    Matcher profileMenuMatcher = ProfileMenuCommands.ChangeCurrency.getMatcher(input);
                    String newCurrency = profileMenuMatcher.group("currency");
                    Result result = controller.changeCurrency(newCurrency);
                    System.out.println(result);
                    break;
                case ChangeUsername:
                    profileMenuMatcher = ProfileMenuCommands.ChangeUsername.getMatcher(input);
                    String newUsername = profileMenuMatcher.group("username");
                    result = controller.changeUsername(newUsername);
                    System.out.println(result);
                    break;
                case ChangePassword:
                    profileMenuMatcher = ProfileMenuCommands.ChangePassword.getMatcher(input);
                    String oldPassword = profileMenuMatcher.group("oldPass");
                    String newPassword = profileMenuMatcher.group("newPass");
                    result = controller.changePassword(oldPassword, newPassword);
                    System.out.println(result);
                    break;
                case Back:
                    System.out.println(controller.back());
                    break;
                case Exit:
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}
