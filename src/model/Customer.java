package model;

import java.util.regex.Pattern;

public class Customer {

    public String firstName;
    public String lastName;
    public String email;
    String emailRegex = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException(
                    "Error, invalid email address!"
            );
        }


        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer name: " + lastName + ", " + firstName + "   email: " + email;
    }
}
