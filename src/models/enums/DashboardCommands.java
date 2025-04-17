package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our dashboard and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum DashboardCommands  {
    GoToLogin("^\\s*go\\s+to\\s+login\\s+menu\\s*$"),
    GoToProfile("^\\s*go\\s+to\\s+profile\\s+menu\\s*$"),
    Logout("^\\s*logout\\s*$"),
    Exit("^\\s*exit\\s*$"),
    ShowGroups("^\\s*show\\s+my\\s+groups\\s*$"),
    CreateGroup("^\\s*create-group\\s+-n\\s+(?<group>[A-Za-z0-9 !@#$%^&*]+)+\\s+-t\\s+(?<type>\\S+)\\s*$"),
    GroupName("^[A-Za-z0-9!@#$%^&*]{1}[A-Za-z0-9 !@#$%^&*]{2,38}[A-Za-z0-9!@#$%^&*]{1}$"),
    AddUser("^\\s*add-user\\s+-u\\s+(?<username>\\S+)\\s*" +
            "-e\\s+(?<email>\\S+)\\s+" +
            "-g\\s+(?<id>-?\\d+)\\s*$"),
    AddExpense("^\\s*add-expense\\s+-g\\s+(?<id>-?\\d+)\\s+-s\\s+(?<equal>(equally|unequally))\\s+" +
            "-t\\s+(?<total>-?.*)\\s+-n\\s+(?<number>-?\\d+)\\s*$"),
    ShowBalance("^\\s*show balance\\s+-u\\s+(?<username>\\S+)\\s*$"),
    SettleUp("^\\s*settle-up\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<money>-?.*)\\s*$");



    private final String pattern;

    DashboardCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
