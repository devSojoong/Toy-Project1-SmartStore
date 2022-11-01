package me.smartstore.project.customers;

import me.smartstore.project.groups.Group;

import java.util.Arrays;
import java.util.Objects;

public class ClassifiedCustomers extends Customers {
    protected Group group;
    protected Customer[] customers;

    public ClassifiedCustomers(){}

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public Customer[] getCustomers() {
        return customers;
    }

    @Override
    public void setCustomers(Customer[] customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassifiedCustomers that = (ClassifiedCustomers) o;
        return group.equals(that.group) && Arrays.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(group);
        result = 31 * result + Arrays.hashCode(customers);
        return result;
    }

    @Override
    public String toString() {
        return "ClassifiedCustomers{" +
                "group=" + group +
                ", customers=" + Arrays.toString(customers) +
                '}';
    }

}