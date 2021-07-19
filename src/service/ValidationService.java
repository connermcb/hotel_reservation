package service;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Reservation;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ValidationService {
    private static ValidationService validationService = null;

    public static final AdminResource adminResource = AdminResource.getInstance();
    public static final HotelResource hotelResource = HotelResource.getInstance();

    private ValidationService() {}

    public static ValidationService getInstance() {
        if (null == validationService) {
            validationService = new ValidationService();
        }
        return validationService;
    }

    public static Integer userInput2Int(Scanner scanner) {
        int userInput = 0;
        Boolean isValid = FALSE;
        while(!isValid) {
            try {
                userInput = Integer.parseInt(scanner.nextLine());
                isValid = TRUE;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid entry, make sure you've entered a number");
            }
        }
        return userInput;
    }

    public static String validateRoomNumber(Scanner scanner) {
        Random random = new Random();
        int roomNumber = 0;
        System.out.println("Enter a room number between 101 and 999...");
        roomNumber = userInput2Int(scanner);

        // Test that room number is within range
        Boolean inRange = FALSE;
        while (!inRange) {
            if ((roomNumber >= 101 && roomNumber <= 999)) {
                inRange = TRUE;
            } else {
                System.out.println("Room number is out of range. Use a number between 101 and 999.");
                roomNumber = userInput2Int(scanner);
            }
        }

        // Test that room number isn't already taken
        Boolean isTaken = TRUE;
        while (isTaken) {
            isTaken = adminResource.getARoom(String.valueOf(roomNumber)) != null;
            if (isTaken) {
                System.out.println("The room number " + String.valueOf(roomNumber) + " is already taken. Try again.");
                roomNumber = userInput2Int(scanner);
            }
        }
        return String.valueOf(roomNumber);
    }

    public static Double validateRoomPrice(Scanner scanner) {
        Boolean isValid = FALSE;
        double roomPrice = 0.0;
        System.out.println("Enter a per night price...");
        roomPrice = userInput2Int(scanner);
        while (!isValid) {
            if (roomPrice < 0) {
                System.out.println("Room prices must be positive. Try again.");
            } else {
                isValid = TRUE;
            }
        }
        return roomPrice;
    }

    public static RoomType validateRoomType(Scanner scanner) {
        //    Code pattern for checking if value in RoomType Enum adapted from:
        //    https://www.linuxcommands.site/java/java-tutorial-java-enum-check-if-value-exists/

        Boolean isValid = FALSE;
        System.out.println("Enter a room type (single or double)...");
        String userInput = scanner.nextLine().toUpperCase();
        while (!isValid) {
            for (RoomType each : RoomType.values()) {
                if (userInput.equals(each.name())) {
                    isValid = TRUE;
                } else {
                    System.out.println("Invalid entry. Please enter either 'double' or 'single'...");
                    userInput = scanner.nextLine().toUpperCase();
                }
            }
        }
        RoomType roomType = RoomType.valueOf(userInput);
        return roomType;
    }

    public static String validateEmailFormat(Scanner scanner) {
        Boolean isValid = FALSE;
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        System.out.println("Please enter an email address?");
        String userInput = new String();

        while (!isValid) {
            userInput = scanner.nextLine();
            if (!pattern.matcher(userInput).matches()) {
                System.out.println("Invalid email address. Please try again.");
            } else {
                isValid = TRUE;
            }
        }
        return userInput;
    }

    public static void validateReservation(Scanner scanner, Collection<IRoom> rooms, String userEmail, Date checkinDate, Date checkoutDate) {
        Boolean isValid = FALSE;

        while (!isValid) {
            String roomSelection = scanner.nextLine();
            System.out.println("Attempting to reserve room " + roomSelection);
            try {
                hotelResource.bookARoom(userEmail, hotelResource.getRoom(roomSelection), checkinDate, checkoutDate);
                System.out.println("Confirming reservation...");
                for (Reservation each : hotelResource.getCustomersReservations(userEmail)) {
                    System.out.println(each.toString());
                }
                isValid = TRUE;
            } catch (Exception e) {
                System.out.println(e.getMessage() + " Please make another selection.");
                for (IRoom room : rooms) {
                    System.out.println(room.toString());
                }
            }
        }
    }
}
