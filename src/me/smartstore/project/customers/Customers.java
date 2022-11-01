package me.smartstore.project.customers;

import me.smartstore.project.groups.GroupType;
import me.smartstore.project.groups.Groups;

import java.util.Arrays;

public class Customers {
    private final int DEFAULT_SIZE = 10;
    protected Customer[] customers;
    private int size = 0; // 실제 배열 capacity
    private int count = 0; // 실제 인스턴스 개수
    private static Customers allCustomers;

    public static Customers getInstance() {
        if (allCustomers == null) {
            allCustomers = new Customers();
        }
        return allCustomers;
    }

    protected Customers() {
        customers = new Customer[DEFAULT_SIZE];
        size = DEFAULT_SIZE;
    }

    protected Customers(int size) {
        customers = new Customer[size];
        this.size = size;
    }

    protected Customers(Customer[] customers) {
        if (customers != null) {
            this.customers = customers;
            count = customers.length;
        }
    }

    public Customer[] getCustomers() {
        return Arrays.copyOf(customers, count);
    }

    public void setCustomers(Customer[] customers) {
        this.customers = customers;
    }

    public int getCount() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void grow() {
        Customer[] copy = Arrays.copyOf(customers, count);
        size *= 2;
        customers = Arrays.copyOf(copy, size);
    }

    public void add(Customer customer) { // 배열 끝에 추가
        if (customer == null) return;

        if (count < size) {
            customers[count] = customer;
            count++;
        } else {
            grow();
            add(customer);
        }
    }

    public void add(int i, Customer customer) {
        if (!(i >= 0 && i <= count)) return;
        if (customer == null) return;

        if (count < size) {
            Customer[] copy = Arrays.copyOfRange(customers, i, count);
            customers[i] = customer;
            count++;

            //System.arraycopy(customers, i + 1, copy, 0, copy.length);
            //System.arraycopy(가져올 애, 가져오는 애 복사 시작점, 받는 애, 받는 애 받을 시작점, 가져올 애 개수)
            System.arraycopy(copy, 0, customers, i + 1, copy.length);

        } else {
            grow();
            add(i, customer);
        }
    }

    public Customer pop(int i) {
        if (!(i >= 0 && i < count)) return null;
        if (isEmpty()) return null;

        Customer[] copy = Arrays.copyOfRange(customers, i + 1, count);
        Customer removeNode = customers[i];
        customers[i] = null;
        count--;
        customers[count] = null;

        //System.arraycopy(customers, i, copy, 0, copy.length);
        System.arraycopy(copy, 0, customers, i, copy.length);
        return removeNode;
    }

    public Customer pop() {
        if (isEmpty()) return null;

        Customer popNode = customers[count - 1];
        customers[count - 1] = null;
        count--;

        return popNode;
    }

    public Customer get(int i) {
        if (!(i >= 0 && i < count)) return null;
        return customers[i];
    }

    public void set(int i, Customer customer) {
        if (!(i >= 0 && i < count)) return;
        if (customer == null) return;
        customers[i] = customer;
    }

    public void print() {
        for (int i = 0; i < count; i++) {
            if (customers[i] != null) {
                System.out.printf("No.  %4d => %s\n", (i + 1), customers[i]);
            }
        }
    }

    public void classify(int menuNum) {
        // TODO: ClassifiedCustomers classify()
        Customer[] cNone = new Customer[customers.length];
        Customer[] cGeneral = new Customer[customers.length];
        Customer[] cVip = new Customer[customers.length];
        Customer[] cVVip = new Customer[customers.length];

        int iNone = 0;
        int iGeneral = 0;
        int iVip = 0;
        int iVVip = 0;
        if (customers.length == 0) return;

        if (!isParameterCheck()) return;

        for (Customer c : customers) {
            if (c == null) break;

            if (Groups.getInstance().get(GroupType.VVIP).getParameter() != null) {
                if (c.getSpentTime() >= Groups.getInstance().get(GroupType.VVIP).getParameter().getMinimumSpentTime() &&
                        c.getTotalPay() >= Groups.getInstance().get(GroupType.VVIP).getParameter().getMinimumTotalPay()) {
                    cVVip[iVVip] = c;
                    iVVip++;
                    continue;
                }
            }
            if (Groups.getInstance().get(GroupType.VIP).getParameter() != null) {
                if (c.getSpentTime() >= Groups.getInstance().get(GroupType.VIP).getParameter().getMinimumSpentTime() &&
                        c.getTotalPay() >= Groups.getInstance().get(GroupType.VIP).getParameter().getMinimumTotalPay()) {
                    cVip[iVip] = c;
                    iVip++;
                    continue;
                }
            }
            if (Groups.getInstance().get(GroupType.GENERAL).getParameter() != null) {
                if (c.getSpentTime() >= Groups.getInstance().get(GroupType.GENERAL).getParameter().getMinimumSpentTime() &&
                        c.getTotalPay() >= Groups.getInstance().get(GroupType.GENERAL).getParameter().getMinimumTotalPay()) {
                    cGeneral[iGeneral] = c;
                    iGeneral++;
                    continue;
                }
            }
            cNone[iNone] = c;
            iNone++;

        }


        if (menuNum == 2) {
            //sorted by name
            cNone = compareString(cNone);
            cGeneral = compareString(cGeneral);
            cVip = compareString(cVip);
            cVVip = compareString(cVVip);

        } else if (menuNum == 3) {
            //sorted by time
            cNone = compareInt(cNone, "time");
            cGeneral = compareInt(cGeneral, "time");
            cVip = compareInt(cVip, "time");
            cVVip = compareInt(cVVip, "time");

        } else if (menuNum == 4) {
            //sorted by money
            cNone = compareInt(cNone, "money");
            cGeneral = compareInt(cGeneral, "money");
            cVip = compareInt(cVip, "money");
            cVVip = compareInt(cVVip, "money");
        }
        //ClassifiedCustomersGroup.getInstance().ClassifiedGroupsinit();
        for (ClassifiedCustomers cf : ClassifiedCustomersGroup.getInstance().classifiedCustomers) {
            if (cf.getGroup().getGroupType() == GroupType.NONE) {
                cf.setCustomers(cNone);
            } else if (cf.getGroup().getGroupType() == GroupType.GENERAL) {
                cf.setCustomers(cGeneral);
            } else if (cf.getGroup().getGroupType() == GroupType.VIP) {
                cf.setCustomers(cVip);
            } else if (cf.getGroup().getGroupType() == GroupType.VVIP) {
                cf.setCustomers(cVVip);
            }
        }
    }

    public Customer[] compareString(Customer[] customers) {
        Customer tempData;
        for (int i = 0; i < customers.length; i++) {
            for (int j = 0; j < customers.length - 1; j++) {
                if (customers[j + 1] == null) break;
                if (customers[j].getName().compareTo(customers[j + 1].getName()) > 0) {
                    tempData = customers[j + 1];
                    customers[j + 1] = customers[j];
                    customers[j] = tempData;
                }
            }
        }
        return customers;
    }

    public Customer[] compareInt(Customer[] customers, String type) {
        Customer tempData;

        for (int i = 0; i < customers.length; i++) {
            for (int j = 0; j < customers.length - 1; j++) {
                if (customers[j + 1] == null) break;
                if (type.equals("money")) {
                    if (customers[j].getTotalPay() > customers[j + 1].getTotalPay()) {
                        tempData = customers[j + 1];
                        customers[j + 1] = customers[j];
                        customers[j] = tempData;
                    }
                } else {
                    if (customers[j].getSpentTime() > customers[j + 1].getSpentTime()) {
                        tempData = customers[j + 1];
                        customers[j + 1] = customers[j];
                        customers[j] = tempData;
                    }
                }
            }
        }
        return customers;
    }

    public boolean isParameterCheck() {
        return Groups.getInstance().getCount() != 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < count; i++) {
            str.append(customers[i]).append("\n");
        }
        return str.toString();
    }
}
