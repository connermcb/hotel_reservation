package model;

public class Tester {

    public static void main(String[] args) {
        Customer customer1 = new Customer("first", "second", "j@domain.com");
        Customer customer2 = new Customer("primero", "segundo", "email");
        System.out.println(customer1);
        System.out.println(customer2);
    }
}
