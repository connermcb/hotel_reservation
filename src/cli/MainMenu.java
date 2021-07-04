package cli;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

public class MainMenu {

    public static HotelResource hotelResource = new HotelResource();

    public static void findReserveRoom() {
        System.out.println("find and reserve room");

    }

    public static void seeReservation(String customerEmail) {
        System.out.println("see reservation");
        Collection<Reservation> reservations = hotelResource.getCustomersReservations(customerEmail);
        for (Reservation each : reservations) {
            each.toString();
        }
    }

    public static void createAccount(String customerEmail, String firstName, String lastName) {
        System.out.println("create account");
        hotelResource.createACustomer(customerEmail, firstName, lastName);
    }

    public static void outputMainMenu() {
        System.out.println("\n-----------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservation");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("-----------------------------\n");
        System.out.println("Please select an number from the menu");
    }
    public static void main(String[] args) {
        Boolean keepPolling = TRUE;
        outputMainMenu();
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepPolling) {

                int userSelection = Integer.parseInt(scanner.nextLine());

                switch (userSelection) {
                    case 1:
                        keepPolling = FALSE;
                        System.out.println("When are you planning on arriving (yyyy/mm/dd) ?");
                        Date checkinDate = hotelResource.parseStrDate(scanner);
                        System.out.println("When are you planning on leaving (yyyy/mm/dd) ?");
                        Date checkoutDate = hotelResource.parseStrDate(scanner);
                        Collection<IRoom> rooms = hotelResource.findARoom(checkinDate, checkoutDate);
                        if (rooms.isEmpty()) {
                            System.out.println("No rooms available for those dates");
                        } else {
                            for (IRoom room : rooms) {
                                System.out.println(room.toString());
                            }
                        }
//                        findReserveRoom();
                        break;
                    case 2:
                        keepPolling = FALSE;
                        System.out.println("We'll need an email address to find your reservation:");
                        String reservationEmail = scanner.nextLine();
                        seeReservation(reservationEmail);
                        break;
                    case 3:
                        keepPolling = FALSE;
                        System.out.println("Okay, let's set up an account for you...");
                        System.out.println("What's your first name?");
                        String firstName = scanner.nextLine();
                        System.out.println("What's your last name?");
                        String lastName = scanner.nextLine();
                        System.out.println("What's a good email address to reach you at?");
                        String accountEmail = scanner.nextLine();
                        createAccount(accountEmail, firstName, lastName);
                        break;
                    case 4:
                        keepPolling = FALSE;
                        AdminMenu.main(null);
                        break;
                    case 5:
                        keepPolling = FALSE;
                        System.out.println("\nThank you for using the Hotel Reservation System!\n");
                        break;
                    default:
                        System.out.println("\nInvalid selection...Please choose a number from the menu\n");
                }
            }
        }
    }
}


