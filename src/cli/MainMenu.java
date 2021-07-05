package cli;

import service.CustomerService;
import service.ReservationService;

import java.util.Scanner;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

public class MainMenu {

    public static final ReservationService reservationService = ReservationService.getInstance();
    public static final CustomerService customerService = CustomerService.getInstance();

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
                        reservationService.findAndReserveRoom(scanner);
                        break;
                    case 2:
                        reservationService.seeReservation(scanner);
                        break;
                    case 3:
                        customerService.createAccount(scanner);
                        break;
                    case 4:
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


