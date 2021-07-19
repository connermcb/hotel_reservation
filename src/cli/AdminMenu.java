package cli;

import java.util.*;


import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.ValidationService;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

public class AdminMenu {

    public static final AdminResource adminResource = AdminResource.getInstance();
    public static final ValidationService validationService = ValidationService.getInstance();

    public static void seeAllCustomers() {
        Map<String, Customer> allCustomers = adminResource.getAllCustomers();
        System.out.println("\nThere is/are " + allCustomers.size() + " Customer(s):");
        for (Map.Entry<String, Customer> entry : allCustomers.entrySet()) {
            Customer customer = entry.getValue();
            System.out.println(customer.toString());
        }
        outputAdminMenu();
    }

    public static void seeAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        System.out.println("\nThere are " + allRooms.size() + " Room(s) in the system:");
        for (IRoom room : allRooms) {
            System.out.println(room.toString());
        }
        outputAdminMenu();

    }

    public static void seeAllReservations() {
        adminResource.displayAllReservations();
        outputAdminMenu();
    }

    public static void addRoom(Scanner scanner) {
        Boolean addMoreRooms = TRUE;
        Collection<IRoom> roomsToAdd = new ArrayList();
        while (addMoreRooms) {
            String roomNumber = validationService.validateRoomNumber(scanner);
            Double price = validationService.validateRoomPrice(scanner);
            RoomType roomType = validationService.validateRoomType(scanner);

            IRoom newRoom = new Room(roomNumber, price, roomType);

            roomsToAdd.add(newRoom);

            System.out.println("Would you like to add another room? (y/n)");
            String addMore = scanner.nextLine();
            switch (addMore) {
                case "y":
                    break;
                case "n":
                    addMoreRooms = FALSE;
                    break;
                default:
                    System.out.println("\nInvalid selection...Please select 'y' for yes or 'n' for no...\n");
            }
        }
        adminResource.addRoom(roomsToAdd);
        System.out.println("Adding " + roomsToAdd.size() + " rooms to reservation system...");
        seeAllRooms();
    }

    public static void buildHotel(Scanner scanner) {
        Random random = new Random();
        Collection<IRoom> roomsToAdd = new ArrayList();
        for (RoomType roomType : RoomType.values()) {
            System.out.println("How many " + roomType.toString().toLowerCase() + " rooms would you like to create?");
            int nRooms = Integer.parseInt(scanner.nextLine());
            System.out.println("What is the minimum price for a room");
            int minPrice = Integer.parseInt(scanner.nextLine());
            System.out.println("What is the maximum price for a room");
            int maxPrice = Integer.parseInt(scanner.nextLine());
            System.out.println("What proportion of rooms should be free?");
            double freeRoomsProp = Double.parseDouble(scanner.nextLine());

            for (int i = 0; i < nRooms; i++) {
                String roomNumber = new String();
                Boolean isTaken = TRUE;
                while (isTaken) {
                    int room = random.ints(101, 999).findFirst().getAsInt();
                    roomNumber = String.valueOf(room);
                    isTaken = adminResource.getARoom(roomNumber) != null;
                }

                double price = 0.0;
                if (Math.random() > freeRoomsProp) {
                    price = random.ints(minPrice, maxPrice).findFirst().getAsInt();
                }

                IRoom newRoom = new Room(roomNumber, price, roomType);
                roomsToAdd.add(newRoom);
            }
        }
        adminResource.addRoom(roomsToAdd);
        seeAllRooms();
    }

    public static void populateTestData() {
        System.out.println("Test data");
    }
    public static void outputAdminMenu() {
        System.out.println("\n-----------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Build a Hotel (populate rooms)");
        System.out.println("6. Populate Test Data (not yet implemented)");
        System.out.println("7. Back to Main Menu");
        System.out.println("-----------------------------\n");
        System.out.println("Please select a number from the menu");
    }

    public static void main(String[] args) {
        Boolean keepPolling = TRUE;
        outputAdminMenu();
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepPolling) {

                int userSelection = Integer.parseInt(scanner.nextLine());

                switch (userSelection) {
                    case 1:
                        seeAllCustomers();
                        break;
                    case 2:
                        seeAllRooms();
                        break;
                    case 3:
                        seeAllReservations();
                        break;
                    case 4:
                        addRoom(scanner);
                        break;
                    case 5:
                        buildHotel(scanner);
                        break;
                    case 6:
                        keepPolling = FALSE;
                        populateTestData();
                        break;
                    case 7:
                        keepPolling = FALSE;
                        MainMenu.main(null);
                        break;
                    default:
                        System.out.println("\nInvalid selection...Please choose a number from the menu\n");
                }
            }
        }
    }
}

