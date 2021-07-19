package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {

    public Customer customer;
    public IRoom room;
    public Date checkInDate;
    public Date checkOutDate;

    public Reservation (Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return "\nReservation: Customer: " + customer +
                ", room number: " + room +
                ", checkin date: " + checkInDate +
                ", checkout date: " + checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return room.equals(that.room) && (
                                          (checkInDate.compareTo(that.checkInDate) >= 0 &&
                                           checkInDate.compareTo(that.checkOutDate) < 0) ||
                                          (checkOutDate.compareTo(that.checkInDate) >= 0 &&
                                           checkOutDate.compareTo(that.checkOutDate) < 0)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room);
    }
}
