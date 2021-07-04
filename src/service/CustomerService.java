package service;

import model.Customer;
import model.IRoom;

import java.util.Collection;
import java.util.Map;

public class CustomerService {

    private static CustomerService customerService = null;
    private Map<String, Customer> customers;

    public static CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }


    public void addCustomer(String email, String firstName, String lastName) {
        this.customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail) {
        return this.customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return (Collection<Customer>) this.customers;
    }
}
