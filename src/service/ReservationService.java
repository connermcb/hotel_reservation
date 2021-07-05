package service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.IRoom;
import model.Reservation;
import model.Customer;
import model.Room;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ReservationService {
    private static ReservationService reservationService = null;

    Collection<IRoom> rooms = new ArrayList();
    Collection<Reservation> reservations = new ArrayList();

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (null == reservationService) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public Collection<IRoom> getAllRooms() {
        return this.rooms;
    }

    public void addRoom(IRoom room) {
        this.rooms.add(room);
    }

    public IRoom getARoom(String roomID) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber() == roomID) {
                return room;
            }
        }
        return null;
    }

    public Date parseStrDate(Scanner scanner) {
        // credit for regex pattern goes to mkyong:
        // https://mkyong.com/regular-expressions/how-to-validate-date-with-regular-expression/
        String dateRegex = "((?:19|20)[0-9][0-9])/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])";
        Pattern datePattern = Pattern.compile(dateRegex);
        Calendar calendar = Calendar.getInstance();
        Boolean isParsed = FALSE;
        while (!isParsed) {
            String dateStr = scanner.nextLine();
            Matcher matcher = datePattern.matcher(dateStr);
            if (!matcher.matches()) {
                String parseExpMessage = new String();
                parseExpMessage = "\nHmm...that doesn't look like the right date format\n" +
                        "Try using yyyy/mm/dd, for example 2021/05/21";
                System.out.println(parseExpMessage);
            } else {
                int year = Integer.parseInt(matcher.group(1));
                int month =  Integer.parseInt(matcher.group(2));
                int day = Integer.parseInt(matcher.group(3));

                calendar.set(year, month, day);

                isParsed = TRUE;
            }
        }
        Date date = calendar.getTime();
        return date;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        this.reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection tmpList = new ArrayList(this.rooms);
        for (Reservation r : reservations) {
            if ((checkInDate.compareTo(r.checkInDate) >= 0 && checkInDate.compareTo(r.checkOutDate) < 0) ||
                    (checkOutDate.compareTo(r.checkInDate) >= 0 && checkOutDate.compareTo(r.checkOutDate) < 0)) {
                tmpList.remove(r.room);
            }
        }
        return tmpList;
    }

    public Collection<Reservation> getCustomersReservation(String customerEmail) {
        List tmpList = new ArrayList();
        for (Reservation r : reservations) {
            if (r.customer.email == customerEmail) {
                tmpList.add(r);
            }
        }
        return tmpList;
    }

    public void printAllReservations() {
        System.out.println("There are " + reservations.size() + " in the reservation system:");
        for (Reservation each : reservations) {
            System.out.println("\t" + each);
        }
    }

}
