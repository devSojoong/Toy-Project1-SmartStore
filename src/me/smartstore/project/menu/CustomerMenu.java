package me.smartstore.project.menu;

import me.smartstore.project.customers.Customer;
import me.smartstore.project.customers.Customers;
import me.smartstore.project.util.ErrorMessage;

import java.util.regex.Pattern;

public class CustomerMenu extends Menu {
    private static CustomerMenu customerMenu;
    private String[] menus
            = {"Set Customer Data", "View Customer Data", "Update Customer Data", "Delete Customer Data", "Back"};
    private String[] menus2;
    /* method reflect */
    private String[] methods = {"setCusData", "viewCusData", "updateCusData", "deleteCusData"};

    private CustomerMenu() {
    }

    public static CustomerMenu getInstance() {
        if (customerMenu == null) {
            customerMenu = new CustomerMenu();
        }
        return customerMenu;
    }

    public void setCusData() {
        System.out.println("** Press -1, if you want to exit! **");
        System.out.print("How many customers to input? ");
        int count = scanner.nextInt();

        menus2 = new String[]{"Customer Name", "Customer ID", "Customer Spent Time", "Customer Total Pay", "Back"};

        AddCustDataFunc(count);
        menus2 = null;

    }

    public void viewCusData() {
        System.out.println("======= Customer Info. =======");
        for (int i = 0; i < Customers.getInstance().getCount(); i++) {
            System.out.print("No." + (i + 1) + " =>");
            System.out.println(Customers.getInstance().get(i).toString());
        }
    }

    public void updateCusData() {
        System.out.println();
        viewCusData();
        if (Customers.getInstance().getCount() == 0) {
            System.out.println(ErrorMessage.ERR_MSG_NO_DATA);
            return;
        }
        while (true) {
            System.out.print("\nWhich customer ( 1 ~ " + Customers.getInstance().getCount() + " )?");

            menus2 = new String[]{"Customer Name", "Customer ID", "Customer Spent Time", "Customer Total Pay", "Back"};
            try {
                int index = 0;
                if(scanner.hasNextInt()) index = scanner.nextInt();
                else scanner.next();
                if ((index > 0) && (index <= Customers.getInstance().getCount())) {
                    UpdateCustDataFunc(index);
                    menus2 = null;
                    break;
                } else System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_RANGE);

            } catch (Exception e) {
                System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_TYPE);
            }
        }

    }

    public void deleteCusData() {
        viewCusData();
        System.out.println();
        if (Customers.getInstance().getCount() == 0) {
            System.out.println(ErrorMessage.ERR_MSG_NO_DATA);
            return;
        }
        while (true) {
            System.out.print("\nWhich customer ( 1 ~ " + Customers.getInstance().getCount() + " )?");
            try {
                int index = 0;
                if(scanner.hasNextInt()) index = scanner.nextInt();
                else scanner.next();

                if ((index > 0) && (index <= Customers.getInstance().getCount())) {
                    Customers.getInstance().pop(index - 1);
                    break;
                } else System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_RANGE);
            } catch (Exception e) {
                System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_TYPE);
            }

        }
    }

    @Override
    public void manage() {
        setInstance(customerMenu);
        setMethods(methods);
        super.manage();
    }

    @Override
    public int dispMenu() {
        if (menus2 == null) {
            setMenus(menus);
        } else
            setMenus(menus2);
        return super.dispMenu();
    }

    public void AddCustDataFunc(int count) {
        int i = 0;
        int choice;
        String name = "";
        String ID = "";
        int spentTime = 0;
        int totalPay = 0;

        while (i < count) {
            System.out.println("\n====== Customer " + (i + 1) + " Info. ======\n");
            choice = dispMenu();
            if (choice == 1) {
                System.out.print("Input Customer's Name: ");
                name = scanner.next();
                if (!Pattern.matches("^[a-zA-Z]{3,15}$", name)) {
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
                    name = "";
                }
            } else if (choice == 2) {
                System.out.print("Input Customer's ID: ");
                ID = scanner.next();
                if (!Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$", ID)) {
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
                    ID = "";
                }
            } else if (choice == 3) {
                System.out.print("Input Customer's spent time: ");
                if(scanner.hasNextInt()) spentTime = scanner.nextInt();
                else {
                    scanner.next();
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
                }
            } else if (choice == 4) {
                System.out.print("Input Customer's total pay: ");
                if(scanner.hasNextInt()) totalPay = scanner.nextInt();
                else {
                    scanner.next();
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
                }
            } else if (choice == 5) {
                if (!name.equals("") && (!ID.equals("")) && spentTime > 0 && totalPay > 0) {
                    Customer customer = new Customer(name, ID, spentTime, totalPay);
                    Customers.getInstance().add(customer);
                    i++;
                    name = "";
                    ID = "";
                    spentTime = 0;
                    totalPay = 0;
                } else {
                    System.out.println("Fill the all info.");
                }
            } else System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_RANGE);
        }
    }

    public void UpdateCustDataFunc(int index) {
        int choice;
        String name;
        String ID;
        int spentTime;
        int totalPay;

        while (true) {
            choice = dispMenu();
            if (choice == 1) {
                System.out.print("Input Customer's Name: ");
                name = scanner.next();
                if (!Pattern.matches("^[a-zA-Z]{3,15}$", name)) {
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
                    name = "";
                } else {
                    Customers.getInstance().get(index - 1).setName(name);
                }
            } else if (choice == 2) {
                System.out.print("Input Customer's ID: ");
                ID = scanner.next();
                if (!Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$", ID)) {
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
                    ID = "";
                } else {
                    Customers.getInstance().get(index - 1).setUserID(ID);
                }
            } else if (choice == 3) {
                System.out.print("Input Customer's spent time: ");
                if(scanner.hasNextInt()) {
                    spentTime = scanner.nextInt();
                    Customers.getInstance().get(index - 1).setSpentTime(spentTime);
                }
                else {
                    scanner.next();
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
                }
            } else if (choice == 4) {
                System.out.print("Input Customer's total pay: ");
                if(scanner.hasNextInt()) {
                    totalPay = scanner.nextInt();
                    Customers.getInstance().get(index - 1).setSpentTime(totalPay);
                }
                else {
                    scanner.next();
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
                }
            } else if (choice == 5) {
                break;
            }

        }
    }
}

