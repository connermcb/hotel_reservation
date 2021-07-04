package model;

public class Room implements IRoom{

    private String roomNumber;
    private Double price;
    private RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Room Num: " + roomNumber + ", Price/Night: " + price + ", Room Type: " + roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomtype() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
