package views;
/*
Explanation:
This is an  interface class for view of menus.
think about this : menus have some common functions that has different implementation in each menu.
so we can define an interface for menus and then implement it in each menu.
 */

import com.ahmz.test.tester.Scanner;  // My Scanner import is necessary

public interface AppMenu {
    public void check(Scanner scanner);
}
