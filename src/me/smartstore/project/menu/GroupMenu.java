package me.smartstore.project.menu;

import me.smartstore.project.groups.Group;
import me.smartstore.project.groups.GroupType;
import me.smartstore.project.groups.Groups;
import me.smartstore.project.groups.Parameter;
import me.smartstore.project.util.ErrorMessage;

public class GroupMenu extends Menu {
    private static GroupMenu groupMenu;
    private String[] menus
            = {"Set Parameter", "View Parameter", "Update Parameter", "Back"};

    private String[] menus2;

    /* method reflect */
    private String[] methods = {"setParam", "viewParam", "updateParam"};

    private GroupMenu() {
    }

    public static GroupMenu getInstance() {
        if (groupMenu == null) {
            groupMenu = new GroupMenu();
        }
        return groupMenu;
    }

    public void setParam() {
        while (true) {
            String sGroup = choiceGroupMenu();
            menus2 = new String[]{"Minimum Spent Time", "Minimum Total Pay", "Back"};

            if (sGroup.equals("general")) {
                setEachGroupParameter(GroupType.GENERAL);
            } else if (sGroup.equals("vip")) {
                setEachGroupParameter(GroupType.VIP);
            } else if (sGroup.equals("vvip")) {
                setEachGroupParameter(GroupType.VVIP);
            } else if (sGroup.equals("end")) {
                menus2 = null;
                break;
            } else System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
        }

    }

    public void viewParam() {
        while (true) {
            String sGroup = choiceGroupMenu();

            if (sGroup.equals("general")) {
                viewEachGroupParameter(GroupType.GENERAL);
            } else if (sGroup.equals("vip")) {
                viewEachGroupParameter(GroupType.VIP);
            } else if (sGroup.equals("vvip")) {
                viewEachGroupParameter(GroupType.VVIP);
            } else if (sGroup.equals("end")) {
                break;
            } else System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
        }
    }

    public void updateParam() {
        while (true) {
            String sGroup = choiceGroupMenu();
            menus2 = new String[]{"Minimum Spent Time", "Minimum Total Pay", "Back"};

            if (sGroup.equals("general")) {
                updateEachGroupParameter(GroupType.GENERAL);
            } else if (sGroup.equals("vip")) {
                updateEachGroupParameter(GroupType.VIP);
            } else if (sGroup.equals("vvip")) {
                updateEachGroupParameter(GroupType.VVIP);
            } else if (sGroup.equals("end")) {
                menus2 = null;
                break;
            } else System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
        }

    }

    @Override
    public void manage() {
        setInstance(groupMenu);
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

    public String choiceGroupMenu() {
        System.out.println("\n** Press 'end', if you want to exit! **");
        System.out.print("Which group (GENERAL, VIP, VVIP)?");
        return scanner.next().toLowerCase();
    }

    public void setEachGroupParameter(GroupType groupType) {
        int minSpentTime = 0;
        int minTotalPay = 0;
        while (true) {

            int choice = dispMenu();
            if (choice == 1) {
                System.out.print("Input Minimum Spent Time: ");
                if (scanner.hasNextInt()) minSpentTime = scanner.nextInt();
                else {
                    scanner.next();
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_TYPE);
                }
            }
            if (choice == 2) {
                System.out.print("Input Minimum Total Pay: ");
                if (scanner.hasNextInt()) minTotalPay = scanner.nextInt();
                else {
                    scanner.next();
                    System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_TYPE);
                }
            }
            if (choice == 3) break;
            if (minSpentTime != 0 && minTotalPay != 0) {
                Parameter parameter = new Parameter(minSpentTime, minTotalPay);
                Group group = new Group(groupType, parameter);
                if (groupType == GroupType.NONE) Groups.getInstance().set(0, group);
                else if (groupType == GroupType.GENERAL) Groups.getInstance().set(1, group);
                else if (groupType == GroupType.VIP) Groups.getInstance().set(2, group);
                else if (groupType == GroupType.VVIP) Groups.getInstance().set(3, group);
                break;
            }
        }
    }


    public void viewEachGroupParameter(GroupType groupType) {
        if (Groups.getInstance().get(groupType).getParameter() == null) {
            System.out.println("Not Exist Group Parameter.");

        } else {
            System.out.println("\n" + "GroupType : " + groupType);
            System.out.println("Parameter : " + Groups.getInstance().get(groupType).getParameter().toString());
        }
    }

    public void updateEachGroupParameter(GroupType groupType) {
        int minSpentTime = 0;
        int minTotalPay = 0;

        if (Groups.getInstance().get(groupType).getParameter() == null) {
            System.out.println(ErrorMessage.ERR_MSG_NO_DATA);
            menus2 = null;
            return;
        }

        int choice = dispMenu();
        if (choice == 1) {
            System.out.print("Input Minimum Spent Time: ");
            if (scanner.hasNextInt()) minSpentTime = scanner.nextInt();
            else {
                scanner.next();
                System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_TYPE);
            }
            Groups.getInstance().get(groupType).getParameter().setMinimumSpentTime(minSpentTime);
        }
        if (choice == 2) {
            System.out.print("Input Minimum Total Pay: ");
            if (scanner.hasNextInt()) minTotalPay = scanner.nextInt();
            else {
                scanner.next();
                System.out.println(ErrorMessage.ERR_MSG_INVALID_INPUT_TYPE);
            }
            Groups.getInstance().get(groupType).getParameter().setMinimumTotalPay(minTotalPay);
        }
    }
}
