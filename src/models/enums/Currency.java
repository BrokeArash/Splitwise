package models.enums;

/*
Explanation:
- We need to define a currency enum.
- currencies in out app are some constants that we need to define them in our code once and use them in our code.
- each currency has some data, put them here and use some methods to work with currencies so simply.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Currency {
    Currency("(?<currency>(GTC|SUD|QTR))");


    private final String pattern;

    Currency(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }

    public int getValue (String currency) {
        if(currency.equals("GTC")){
            return 1;
        } else if (currency.equals("SUD")) {
            return 2;
        } else {
            return 5;
        }
    }

}
