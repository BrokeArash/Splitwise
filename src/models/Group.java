package models;
/*
Explanation:
- In our app, we have groups that have some information.
- Group is something that we need to make it an object because it looks like an object (:
- put those information here and use them in your code.
 */

import java.util.ArrayList;

public class
Group {
    public final String name;
    public final int id;
    public final User creator;
    public final String type;
    private static int lastAssigned = 0;

    public Group(String name, User creator, String type) {
        this.name = name;
        this.id = ++lastAssigned;
        this.creator = creator;
        this.type = type;
    }

    public ArrayList<User> members = new ArrayList<>();
}
