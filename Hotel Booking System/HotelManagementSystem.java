import java.util.ArrayList;
import java.util.List;

abstract class Room {
    private int roomNumber;
    private boolean isAvailable;
    protected double price;

    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract double calculatePrice();

    public void displayRoomInfo() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Price: " + price);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Booked"));
    }
}

class SingleRoom extends Room {
    public SingleRoom(int roomNumber) {
        super(roomNumber, 100);
    }

    @Override
    public double calculatePrice() {
        return price;
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(int roomNumber) {
        super(roomNumber, 150);
    }

    @Override
    public double calculatePrice() {
        return price;
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(int roomNumber) {
        super(roomNumber, 300);
    }

    @Override
    public double calculatePrice() {
        return price;
    }
}

class Booking {
    private Room room;
    private String customerName;

    public Booking(Room room, String customerName) {
        this.room = room;
        this.customerName = customerName;
        room.setAvailable(false); 
    }

    public void cancelBooking() {
        room.setAvailable(true);
        System.out.println("Booking for " + customerName + " has been canceled.");
    }

    public void displayBookingDetails() {
        System.out.println("Booking Details:");
        System.out.println("Customer Name: " + customerName);
        room.displayRoomInfo();
    }
}

// Hotel class to manage rooms and bookings
public class HotelManagementSystem {
    private List<Room> rooms;
    private List<Booking> bookings;

    public HotelManagementSystem() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void viewAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                room.displayRoomInfo();
            }
        }
    }

    public void bookRoom(int roomNumber, String customerName) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                Booking booking = new Booking(room, customerName);
                bookings.add(booking);
                System.out.println("Room booked successfully!");
                return;
            }
        }
        System.out.println("Room not available or doesn't exist.");
    }

    public void cancelBooking(int roomNumber, String customerName) {
        for (Booking booking : bookings) {
            if (booking.room.getRoomNumber() == roomNumber && booking.customerName.equals(customerName)) {
                booking.cancelBooking();
                bookings.remove(booking);
                return;
            }
        }
        System.out.println("No booking found for the provided details.");
    }

    public static void main(String[] args) {
        HotelManagementSystem hotel = new HotelManagementSystem();

        hotel.addRoom(new SingleRoom(101));
        hotel.addRoom(new DoubleRoom(102));
        hotel.addRoom(new SuiteRoom(201));

        hotel.viewAvailableRooms();

        hotel.bookRoom(101, "Alice");
        hotel.bookRoom(201, "Bob");

        hotel.viewAvailableRooms();

        hotel.cancelBooking(101, "Alice");

        hotel.viewAvailableRooms();
    }
}
