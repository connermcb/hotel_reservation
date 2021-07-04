package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class HotelResource {

    public static HotelResource hotelResource = null;
    public CustomerService customerService = new CustomerService();
    public ReservationService resrvService = new ReservationService();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        Customer newCustomer = new Customer(firstName, lastName, email);
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return resrvService.getARoom(roomNumber);
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
}

