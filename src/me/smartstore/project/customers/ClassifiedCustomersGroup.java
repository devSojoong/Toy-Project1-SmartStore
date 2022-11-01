package me.smartstore.project.customers;

import me.smartstore.project.groups.GroupType;
import me.smartstore.project.groups.Groups;

public class ClassifiedCustomersGroup {
    public ClassifiedCustomers[] classifiedCustomers;
    private static ClassifiedCustomersGroup classifiedCustomersGroup;

    private ClassifiedCustomersGroup() {
    }

    public static ClassifiedCustomersGroup getInstance() {
        if (classifiedCustomersGroup == null) {
            classifiedCustomersGroup = new ClassifiedCustomersGroup();
        }
        return classifiedCustomersGroup;
    }

    public void ClassifiedGroupsinit(){
        ClassifiedCustomers none = new ClassifiedCustomers();
        none.setGroup(Groups.getInstance().get(GroupType.NONE));

        ClassifiedCustomers general = new ClassifiedCustomers();
        general.setGroup(Groups.getInstance().get(GroupType.GENERAL));

        ClassifiedCustomers vip = new ClassifiedCustomers();
        vip.setGroup(Groups.getInstance().get(GroupType.VIP));

        ClassifiedCustomers vvip = new ClassifiedCustomers();
        vvip.setGroup(Groups.getInstance().get(GroupType.VVIP));

        classifiedCustomers = new ClassifiedCustomers[]{none,general,vip,vvip};

    }
}
