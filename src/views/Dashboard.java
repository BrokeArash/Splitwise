package views;
/*
Explanation:
- This is a view class for the dashboard.
- This class should use to check inputs and print outputs for the dashboard.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.DashboardController;
import models.App;
import models.Expense;
import models.Group;
import models.Result;
import models.enums.DashboardCommands;
import java.util.ArrayList;
import com.ahmz.test.tester.Scanner;  // My Scanner import is necessary
import java.util.regex.Matcher;

public class Dashboard implements AppMenu{
    private final DashboardController controller = new DashboardController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        DashboardCommands matchedCommand = null;
        for (DashboardCommands command : DashboardCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher != null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }

        if (matchedCommand == null ||
        matchedCommand == DashboardCommands.GroupName) {
            System.out.println("invalid command!");
        } else {
            switch (matchedCommand) {
                case CreateGroup:
                    Matcher dashboardMatcher = DashboardCommands.CreateGroup.getMatcher(input);
                    String groupName = dashboardMatcher.group("group");
                    String type = dashboardMatcher.group("type");
                    String trimmedGroupName = groupName.trim();
                    Result result = controller.createGroup(trimmedGroupName, type);
                    System.out.println(result);
                    break;
                case ShowGroups:
                    controller.showGroups();
                    break;
                case AddUser:
                    dashboardMatcher = DashboardCommands.AddUser.getMatcher(input);
                    String username = dashboardMatcher.group("username");
                    String email = dashboardMatcher.group("email");
                    int id = Integer.parseInt(dashboardMatcher.group("id"));
                    result = controller.addUser(username, email, id);
                    System.out.println(result);
                    break;
                case AddExpense: /*might be wrong TODO: check later*/
                    ArrayList<String> namesTemp = new ArrayList<>();
                    ArrayList<Long> moneyTemp = new ArrayList<>();
                    dashboardMatcher = DashboardCommands.AddExpense.getMatcher(input);
                    id = Integer.parseInt(dashboardMatcher.group("id"));
                    String isEqual = dashboardMatcher.group("equal");
                    int numUser = Integer.parseInt(dashboardMatcher.group("number"));
                    String totalTemp = dashboardMatcher.group("total");
                    boolean isMoneyValid = Expense.checkValidNumber(totalTemp.trim());
                    long total = 0;
                    if (isMoneyValid) {
                        total = Long.parseLong(totalTemp.trim());
                    }
                    int sum = 0;
                    boolean isValidExpense = true;
                    Group group = getUserGroupByID(id);
                    if (isEqual.equals("equally")) {
                        for (int i = 0; i < numUser; i++) {
                            namesTemp.add(scanner.nextLine());
                        }
                    } else {
                        for (int i = 0; i < numUser; i++) {
                            String tmp = scanner.nextLine();
                            String[] temp = tmp.split(" ");
                            namesTemp.add(temp[0].trim());
                            String unequallyMoneyTemp = temp[1];
                            boolean isMonetValidTemp = Expense.checkValidNumber(unequallyMoneyTemp.trim());
                            if (temp.length > 2 || !isMonetValidTemp) {
                                isValidExpense = false;
                            }
                            if (isValidExpense) {
                                moneyTemp.add(Long.parseLong(temp[1]));
                                sum += moneyTemp.get(i); //TODO: might get wrong
                            }
                        }
                    }
                    if (group == null) {
                        System.out.println(new Result(false, "group not found!"));
                        break;
                    }

                    result = controller.addExpense(id, isEqual, total, numUser, namesTemp, moneyTemp, sum, isValidExpense);
                    if (!result.message().isEmpty())
                        System.out.println(result);

                    break;
                case ShowBalance:
                    dashboardMatcher = DashboardCommands.ShowBalance.getMatcher(input);
                    username = dashboardMatcher.group("username");
                    result = controller.showBalance(username);
                    System.out.println(result);
                    break;
                case SettleUp:
                    dashboardMatcher = DashboardCommands.SettleUp.getMatcher(input);
                    username = dashboardMatcher.group("username");
                    String inputMoneyTemp = dashboardMatcher.group("money");
                    long inputMoney = 0;
                    boolean isValid = Expense.checkValidNumber(inputMoneyTemp);
                    if (isValid){
                        inputMoney = Long.parseLong(inputMoneyTemp);
                    }
                    result = controller.settleUp(username, inputMoney, isValid);
                    System.out.println(result);
                    break;
                case GoToProfile:
                    System.out.println(controller.goToProfile());
                    break;
                case Logout:
                    System.out.println(controller.logOut());
                    break;
                case Exit:
                    System.exit(0);
                default:
                    break;
            }
        }
    }

    private Group getUserGroupByID(int id) {
        for (Group group : App.getLoggedInUser().groups) {
            if(group.id == id) {
                return group;
            }
        }
        return null;
    }
}
