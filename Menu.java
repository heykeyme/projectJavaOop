import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    Customers customer;
    Room room = new Room();
    Scanner input = new Scanner(System.in);
    Booking booking;
    ArrayList<Request> requests = new ArrayList<>();

    public static void main(String[] args) {
        Menu Pelayan = new Menu();
        Scanner input = new Scanner(System.in);
        int menu;
        String exit;
        do {
            System.out.println("WELCOME TO HAKIMI HOTEL");
            System.out.println("+------------------+");
            System.out.println("|       Menu       |");
            System.out.println("+------------------+");
            System.out.println("| 1. About Us      |");
            System.out.println("| 2. View Our Room |");
            System.out.println("| 3. Make Booking  |");
            System.out.println("| 4. Print Receipt |");
            System.out.println("| 5. Request       |");
            System.out.println("| 6. Exit          |");
            System.out.println("+------------------+");

            System.out.print("Please Enter Number From Menu: ");
            menu = input.nextInt();

            if (menu == 1) {
                // about us section
                Pelayan.aboutUs();
            } else if (menu == 2) {
                // view room section
                Pelayan.viewRoom();
            } else if (menu == 3) {
                // Make booking
                Pelayan.createBooking();
            } else if (menu == 4) {
                // print receipt
                Pelayan.displayReceipt();
            } else if (menu == 5) {
                // request section
                Pelayan.handleRequest();
            } else if (menu == 6) {
                // exit section
                System.out.print("Are You Sure to Exit (y/n): ");
                exit = input.next();
                if (exit.equalsIgnoreCase("y")) {
                    System.out.println("Thank You For Visiting Us! Goodbye!");
                    break;
                }
            } else {
                System.out.println("Your input is invalid! Please try again.");
            }
        } while (true);

        input.close();
    }

    public void aboutUs() {
        System.out.println("This is Hakimi's Hotel, We have run our business for almost 25 Years.");
    }

    // method view Room
    public void viewRoom(){
        System.out.println("+-------------------------------------+------------------+");
        System.out.println("| THIS IS OUR TYPE OF ROOM AVAILABLE  |  PRICE PER NIGHT |");
        System.out.println("+-------------------------------------+------------------+");
        System.out.println("| 1. STANDARD                         |  RM 150          |");
        System.out.println("| 2. DOUBLE                           |  RM 175          |");
        System.out.println("| 3. DOUBLE & SINGLE                  |  RM 200          |");
        System.out.println("| 4. KING                             |  RM 230          |");
        System.out.println("+-------------------------------------+------------------+");
    }

    // create booking method
    public void createBooking() {
        System.out.println("Let's Make a Booking For You Sir/Madam");
        System.out.print("Name: ");
        String name = input.next();
        System.out.print("IC: ");
        double ic = input.nextDouble();
        System.out.print("Address: ");
        input.nextLine();  // Consume the newline character
        String address = input.nextLine();
        customer = new Customers(ic, name, address);

        viewRoom();

        System.out.print("Enter the code of the room you are interested in: ");
        int roomTypeCode = input.nextInt();
        if (roomTypeCode > 0 && roomTypeCode < room.roomType.length) {
            String roomType = room.roomType[roomTypeCode];
            System.out.print("Enter number of nights: ");
            int numberOfNights = input.nextInt();
            booking = new Booking(1, customer, roomType, numberOfNights);  // Booking ID is hardcoded for simplicity
            System.out.println("Booking created successfully!");
        } else {
            System.out.println("Invalid room selection. Please try again.");
        }
    }

    public void displayReceipt() {
        System.out.println("====================");
        System.out.println("This is Your Receipt");
        System.out.println("====================");
        if (booking != null) {
            booking.displayBookingDetails();
        } else {
            System.out.println("No booking information available.");
        }
    }

    // handle request method
    public void handleRequest() {
        System.out.println("Request Section");
        System.out.print("Enter Request Type (e.g., Food, Maintenance, etc.): ");
        input.nextLine(); // Consume the newline character
        String requestType = input.nextLine();
        System.out.print("Enter Request Description: ");
        String description = input.nextLine();
        Request request = new Request(requests.size() + 1, customer, requestType, description);
        requests.add(request);
        System.out.println("Request submitted successfully!");

        System.out.println("Current Requests:");
        for (Request req : requests) {
            req.displayRequestDetails();
        }
    }
}

// customer object
class Customers {
    private String name, address;
    private double ic;

    public Customers(double ic, String name, String address) {
        this.ic = ic;
        this.name = name;
        this.address = address;
    }

    // getter
    public double getIc() {
        return ic;
    }

    public String getName() {
        return name;
    }

    public String getAdd() {
        return address;
    }
}

class Room {
    String[] roomType = {"", "STANDARD", "DOUBLE", "DOUBLE & SINGLE", "KING"};
    double[] roomPrice = {0, 150, 175, 200, 230};
}

class Booking {
    // Data members
    private int bookingID;
    private Customers customer;
    private String roomType;
    private int numberOfNights;
    private double totalCost;

    // Constructor
    public Booking(int bookingID, Customers customer, String roomType, int numberOfNights) {
        this.bookingID = bookingID;
        this.customer = customer;
        this.roomType = roomType;
        this.numberOfNights = numberOfNights;
        this.totalCost = calculateTotalCost();
    }

    // Method to calculate the total cost
    private double calculateTotalCost() {
        double costPerNight = 0;
        switch (roomType) {
            case "STANDARD":
                costPerNight = 150;
                break;
            case "DOUBLE":
                costPerNight = 175;
                break;
            case "DOUBLE & SINGLE":
                costPerNight = 200;
                break;
            case "KING":
                costPerNight = 230;
                break;
            default:
                System.out.println("Invalid room type.");
        }
        return costPerNight * numberOfNights;
    }

    // Method to set the number of nights
    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
        this.totalCost = calculateTotalCost();
    }

    // Method to get the number of nights
    public int getNumberOfNights() {
        return numberOfNights;
    }

    // Method to get the total cost
    public double getTotalCost() {
        return totalCost;
    }

    // Method to display booking details
    public void displayBookingDetails() {
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Nights: " + numberOfNights);
        System.out.println("Total Cost: RM " + totalCost);
    }

    // Additional method to set the room type
    public void setRoomType(String roomType) {
        this.roomType = roomType;
        this.totalCost = calculateTotalCost();
    }

    // Method to get the room type
    public String getRoomType() {
        return roomType;
    }
}

// request object
class Request {
    private int requestID;
    private Customers customer;
    private String requestType;
    private String description;

    public Request(int requestID, Customers customer, String requestType, String description) {
        this.requestID = requestID;
        this.customer = customer;
        this.requestType = requestType;
        this.description = description;
    }

    public int getRequestID() {
        return requestID;
    }

    public Customers getCustomer() {
        return customer;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getDescription() {
        return description;
    }

    public void displayRequestDetails() {
        System.out.println("Request ID: " + requestID);
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Request Type: " + requestType);
        System.out.println("Description: " + description);
    }
}
