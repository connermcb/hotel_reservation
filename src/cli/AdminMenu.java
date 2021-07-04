package cli;

import java.util.Collection;

import java.util.Scanner;

import api.AdminResource;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

public class AdminMenu {

    public AdminResource adminResource = new AdminResource();

    public static void seeAllCustomers() {
        System.out.println("See all customers");
    }

    public static void seeAllRooms() {
        System.out.println("See all rooms");
    }

    public static void seeAllReservations() {
        System.out.println("See all reservations");
    }

    public static void addRoom() {
        System.out.println("Add room");
    }

    public static void outputAdminMenu() {
        System.out.println("\n-----------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("-----------------------------\n");
        System.out.println("Please select an number from the menu");
    }

    public static void main(String[] args) {
        Boolean keepPolling = TRUE;
        outputAdminMenu();
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepPolling) {

                int userSelection = Integer.parseInt(scanner.nextLine());

                switch (userSelection) {
                    case 1:
                        keepPolling = FALSE;
                        seeAllCustomers();
                        break;
                    case 2:
                        keepPolling = FALSE;
                        seeAllRooms();
                        break;
                    case 3:
                        keepPolling = FALSE;
                        seeAllReservations();
                        break;
                    case 4:
                        keepPolling = FALSE;
                        addRoom();
                        break;
                    case 5:
                        keepPolling = FALSE;
                        MainMenu.main(null);
                }
            }
        }
    }
}

