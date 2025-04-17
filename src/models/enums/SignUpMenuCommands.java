package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our sign-up menu and these commands need regexes to be checked.
- put those regexes here and use them in your code.
- these regexes need some functions, put those functions in here.
 */
public enum SignUpMenuCommands {

    Exit("\\s*exit\\s*"),
    GoToLogin("^\\s*go\\s+to\\s+login\\s+menu\\s*$"),
    MainRegister("^\\s*register\\s+-u\\s+(?<username>.*)\\s+-p+\\s+(?<password>.*)\\s+-e+\\s+" +
            "(?<email>.*)\\s+-n+\\s+(?<name>.*)\\s*$"),
    CheckUser("^\\s*[A-Za-z][A-Za-z0-9._-]{3,9}\\s*$"),
    CheckPass("^\\s*(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6,12}\\s*$"),
    CheckEmail("^\\s*[A-Za-z][A-Za-z0-9._-]{3,9}@[a-z]+([.-][a-z]{1,6})?\\.(com|edu|net|org)\\s*$"),
    CheckName("^\\s*[A-Za-z]+(?:-[A-Za-z]+)*\\s*$");


    private final String pattern;

    SignUpMenuCommands(String pattern) {
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