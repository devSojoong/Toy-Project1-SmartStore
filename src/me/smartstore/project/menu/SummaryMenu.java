package me.smartstore.project.menu;

import me.smartstore.project.customers.ClassifiedCustomers;
import me.smartstore.project.customers.ClassifiedCustomersGroup;
import me.smartstore.project.customers.Customer;
import me.smartstore.project.customers.Customers;
import me.smartstore.project.util.ErrorMessage;

public class SummaryMenu extends Menu {
    private static SummaryMenu summaryMenu;

    private String[] menus
            = {"Summary",
            "Summary (Sorted By Name)",
            "Summary (Sorted By Spent Time)",
            "Summary (Sorted By Total Payment)",
            "Back"};

    /* method reflect */
    private String[] methods = {"summary", "sortedByName", "sortedBySpentTime", "sortedByTotalPayment"};

    private SummaryMenu() {
    }

    public static SummaryMenu getInstance() {
        if (summaryMenu == null) {
            summaryMenu = new SummaryMenu();
        }
        return summaryMenu;
    }

    public void summary() {
        SummaryFunc(OrderType.ASCENDING, 1);
    }

    public void sortedByName() {

        SummaryFunc(setOrderType(), 2);
    }

    public void sortedBySpentTime() {
        SummaryFunc(setOrderType(), 3);
    }

    public void sortedByTotalPayment() {
        SummaryFunc(setOrderType(), 4);
    }

    @Override
    public void manage() {
        setInstance(summaryMenu);
        setMethods(methods);
        super.manage();
    }

    @Override
    public int dispMenu() {
        setMenus(menus);
        return super.dispMenu();
    }

    public void SummaryFunc(OrderType orderType, int menuNum) {
        if(orderType == null) return;
        Customers.getInstance().classify(menuNum);

        if(ClassifiedCustomersGroup.getInstance().classifiedCustomers == null) {
            System.out.println("There are no parameters.");
            return;
        }
        for (ClassifiedCustomers cf : ClassifiedCustomersGroup.getInstance().classifiedCustomers) {
            if (cf.getCustomers() == null) {
                System.out.println("==============================");
                System.out.println(cf.getGroup().getGroupType() + " : 0 customer(s)");
                System.out.println("[Parameter] " + cf.getGroup().getParameter().toString());
                System.out.println("------------------------------");
                System.out.println("No customer.");
            } else {

                int count = 0;
                for (Customer c : cf.getCustomers()) {
                    if (c != null) count++;
                }
                System.out.println("\n==============================");
                System.out.println(cf.getGroup().getGroupType() + " : " + count + " customer(s)");
                System.out.println("------------------------------");

                if (orderType == OrderType.DESCENDING) {

                    for (int i = count-1; i >= 0; i--) {

                        if (cf.getCustomers()[i] != null) {
                            System.out.println("No. " + (count-i) + " =>" + cf.getCustomers()[i].toString());

                        } else break;
                    }

                } else {
                    for (int i = 0; i < count; i++) {

                        if (cf.getCustomers()[i] != null) {
                            System.out.println("No. " + (i + 1) + " =>" + cf.getCustomers()[i].toString());

                        } else break;
                    }
                }
            }

        }
    }

    public OrderType setOrderType() {
        while (true) {
            System.out.println("** Press end, if you want to exit! **");
            System.out.print("Which order (ASCENDING, DESCENDING)? ");
            String orderType = scanner.next().toUpperCase();

            if (orderType.equals("ASCENDING"))
                return OrderType.ASCENDING;
            else if (orderType.equals("DESCENDING"))
                return OrderType.DESCENDING;
            else if (orderType.equals("END"))
                return null;
            else System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_TYPE);
        }
    }
}
