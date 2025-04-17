package models.enums;

import views.*;

import com.ahmz.test.tester.Scanner;  // My Scanner import is necessary

/*
Explanation:
- In your code, you have some menus that are constants, and we move between them.
- a good way to handle this is to use enums to define them and use them in your code.
 */
public enum Menu {
    SignUp (new SignUpMenu()),
    LogIn (new LoginMenu()),
    Dashboard (new Dashboard()),
    Profile (new ProfileMenu()),
    Exit (new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }


}
