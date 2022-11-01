import me.smartstore.project.customers.ClassifiedCustomersGroup;
import me.smartstore.project.customers.Customer;
import me.smartstore.project.customers.Customers;
import me.smartstore.project.groups.Group;
import me.smartstore.project.groups.GroupType;
import me.smartstore.project.groups.Groups;
import me.smartstore.project.groups.Parameter;
import me.smartstore.project.menu.CustomerMenu;
import me.smartstore.project.menu.GroupMenu;
import me.smartstore.project.menu.Menu;
import me.smartstore.project.menu.SummaryMenu;
import me.smartstore.project.util.ErrorMessage;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class SmartStoreApplication  {

    private static SmartStoreApplication smartStoreApp;

    /* customers object */
    private Customers allCustomers = Customers.getInstance();

    /* groups object */
    private Groups allGroups = Groups.getInstance();

    /* ClassifiedCustomersGroup object */
    private ClassifiedCustomersGroup allCfGroup = ClassifiedCustomersGroup.getInstance();

    /* menu object */
    private Menu menu = Menu.getInstance();

    /* menu object */
    private GroupMenu groupMenu = GroupMenu.getInstance();
    private CustomerMenu customerMenu = CustomerMenu.getInstance();
    private SummaryMenu summaryMenu = SummaryMenu.getInstance();

    /* method reflect */
    private Class[] classes = { GroupMenu.class, CustomerMenu.class, SummaryMenu.class };
    private Object[] instances = { groupMenu, customerMenu, summaryMenu };
    private String[] methods = { "manage", "manage", "manage" };


    private SmartStoreApplication() {
    }

    public static SmartStoreApplication getInstance() {
        if (smartStoreApp == null) {
            smartStoreApp = new SmartStoreApplication();
        }
        return smartStoreApp;
    }

    public void details() {
        System.out.println("===========================================");
        System.out.println(" Title : SmartStore Customer Classification");
        System.out.println("===========================================\n");
    }


    public SmartStoreApplication test() {
        allGroups.add(new Group(GroupType.NONE, new Parameter(0, 0)));
        allGroups.add(new Group(GroupType.GENERAL, new Parameter(10, 100000)));
        allGroups.add(new Group(GroupType.VIP, new Parameter(20, 200000)));
        allGroups.add(new Group(GroupType.VVIP, new Parameter(30, 300000)));

        Random random = new Random(); // 랜덤 객체 생성

        ClassifiedCustomersGroup.getInstance().ClassifiedGroupsinit();
        for (int i = 20; i > 0; --i) {
            random.setSeed(i);
            allCustomers.add(new Customer("" + (char) (97 + random.nextInt(20)), (char) (97 + random.nextInt(20)) + "12345", random.nextInt(20) * 5, random.nextInt(20) * 50000));
        }
        return this;
    }

    /*
     * this is method for running application.
     * */
    public void run() {
        ClassifiedCustomersGroup.getInstance().ClassifiedGroupsinit();
        details();
        while (true) {
            int choice = menu.dispMenu();
            if (!(choice >= 1 && choice <= methods.length + 1)) { // + 1은 break 포함
                System.out.println("\n" + ErrorMessage.ERR_MSG_INVALID_INPUT_RANGE);
            }
            if (choice == methods.length + 1) {
                break; // back
            }

            try {
                int i = choice - 1;
                classes[i].getMethod(methods[i]).invoke(instances[i]);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
