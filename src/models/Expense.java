package models;
/*
Explanation:
- when we create an expense, we need to store some information about it.
- Expense is something that we need to make it an object.
- put those information here and use them in your code.
 */

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expense{

    public static boolean checkValidNumber(String str) {
        // Check if the string contains only digits
        return str.matches("\\d+");

    }
}
