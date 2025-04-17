package models.enums;
/*
Explanation:
- we have commands in our profile menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    ShowUserInfo ("^\\s*show\\s+user\\s+info\\s*$"),
    ChangeCurrency("^\\s*change-currency\\s+-n\\s+(?<currency>\\S+)\\s*$"),
    ChangeUsername("^\\s*change-username\\s+-n\\s+(?<username>\\S+)\\s*$"),
    ChangePassword("^\\s*change-password\\s+-o\\s+(?<oldPass>\\S+)\\s+-n\\s+(?<newPass>\\S+)\\s*$"),
    Back("^\\s*back\\s*$"),
    Exit("^\\s*exit\\s*$"),
    Username("^(?<username>[A-Za-z][A-Za-z0-9._-]{3,9})$"),
    Password("^(?<password>(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6,12})$");



    private final String pattern;

    ProfileMenuCommands(String pattern) {
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
