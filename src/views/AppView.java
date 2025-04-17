package views;

/*
Explanation:
- This is a view class for the App.
- our app needs a place that handle menus (:
 */

import models.App;
import models.enums.Menu;

import com.ahmz.test.tester.Scanner;  // My Scanner import is necessary

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().checkCommand(scanner);
            /*Reg successful go to login*/
            if (App.getCurrentMenu() == Menu.SignUp && App.isRegSuccessful()) {
                App.setRegSuccessful(false);
                App.setCurrentMenu(Menu.LogIn);
            }
            /*Go To LogIn*/
            else if (App.getCurrentMenu() == Menu.SignUp && App.isGoToLogInRequested()){
                App.setGoToLogInRequested(false);
                App.setCurrentMenu(Menu.LogIn);
            }
            /*Go To SignUp*/
            else if (App.getCurrentMenu() == Menu.LogIn && App.isGoToSignUpRequested()) {
                App.setGoToSignUpRequested(false);
                App.setCurrentMenu(Menu.SignUp);
            }
            /*Login Successful go to Dashboard*/
            else if (App.getCurrentMenu() == Menu.LogIn && App.isLogSuccessful()) {
                App.setLogSuccessful(false);
                App.setCurrentMenu(Menu.Dashboard);
            }
            /*Go to profile*/
            else if (App.getCurrentMenu() == Menu.Dashboard && App.isGoToProfileRequested()) {
                App.setGoToProfileRequested(false);
                App.setCurrentMenu(Menu.Profile);
            }
            /*Go to LoginMenu*/
            else if (App.getCurrentMenu() == Menu.Dashboard && App.isLogOutRequested()) {
                App.setLogOutRequested(false);
                App.setCurrentMenu(Menu.LogIn);
            }
            /*Back to Dashboard*/
            else if (App.getCurrentMenu() == Menu.Profile && App.isBackRequested()) {
                App.setBackRequested(false);
                App.setCurrentMenu(Menu.Dashboard);
            }
        }

        while (App.getCurrentMenu() != Menu.Exit);
    }
}
