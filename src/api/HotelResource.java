package api;

import cli.MainMenu;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import service.ValidationService;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import static cli.AdminMenu.validationService;

public class HotelResource {

    private static HotelResource hotelResource = null;

    public static final CustomerService customerService = CustomerService.getInstance();
    public static final ReservationService resrvService = ReservationService.getInstance();
    public static final ValidationService validationService = ValidationService.getInstance();

    private HotelResource() {}

    public static HotelResource getInstance() {
        if (null == hotelResource) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        Customer newCustomer = new Customer(firstName, lastName, email);
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        IRoom room = resrvService.getARoom(roomNumber);
        System.out.println(room.getRoomNumber());
        return room;
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = this.getCustomer(customerEmail);
        return resrvService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return resrvService.getCustomersReservation(customerEmail);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return resrvService.findRooms(checkInDate, checkOutDate);
    }

    public Date parseStrDate(Scanner scanner) {
        return resrvService.parseStrDate(scanner);
    }

    public void createAccount(Scanner scanner) {
        System.out.println("Okay, let's set up an account for you...");
        System.out.println("What's your first name?");
        String firstName = scanner.nextLine();
        System.out.println("What's your last name?");
        String lastName = scanner.nextLine();
        String customerEmail = validationService.validateEmailFormat(scanner);
        if (customerService.getCustomer(customerEmail) != null) {
            System.out.println("An account already exists for that email");
            MainMenu.outputMainMenu();
        } else {
            customerService.addCustomer(customerEmail, firstName, lastName);
            System.out.println("\nAccount created for " + firstName + " " + lastName + ":");
            Customer newCustomer = getCustomer(customerEmail);
            System.out.println(newCustomer.toString());
            MainMenu.outputMainMenu();
        }
    }

    public void seeReservation(Scanner scanner) {
        System.out.println("We'll need an email address to find your reservation:");
        String reservationEmail = scanner.nextLine();
        for (Reservation each : hotelResource.getCustomersReservations(reservationEmail)) {
            System.out.println(each.toString());
        }
    }

    public void findAndReserveRoom(Scanner scanner) {
        System.out.println("What's your email address so we can pull up your account?");
        String userEmail = scanner.nextLine();
        if (getCustomer(userEmail) == null) {
            System.out.println("Looks like you don't have an account yet." +
                    " Please go back to main menu to open one before booking.");
            MainMenu.outputMainMenu();
        } else {
            System.out.println("When are you planning on arriving (yyyy/mm/dd) ?");
            Date checkinDate = parseStrDate(scanner);
            System.out.println("When are you planning on leaving (yyyy/mm/dd) ?");
            Date checkoutDate = parseStrDate(scanner);
            Collection<IRoom> rooms = findARoom(checkinDate, checkoutDate);
            if (rooms.isEmpty()) {
                System.out.println("No rooms available for those dates");
            } else {
                for (IRoom room : rooms) {
                    System.out.println(room.toString());
                }
            }
            System.out.println("Please select a room from options.");
            validationService.validateReservation(scanner, rooms ,userEmail, checkinDate, checkoutDate);

            MainMenu.outputMainMenu();
        }
    }
}

