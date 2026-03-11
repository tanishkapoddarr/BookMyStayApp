import java.util.*;
public class BookMyStayApp {
    private Set<String> allocatedRooms = new HashSet<>();
    private Map<String, List<String>> roomTypeMapping = new HashMap<>();
    private Queue<BookingRequest> requestQueue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();
    public BookMyStayApp() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }static class BookingRequest {
        String guestName;
        String roomType;
        BookingRequest(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }public void processAllocations() {
        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll();
            if (inventory.getOrDefault(request.roomType, 0) > 0) {
                String roomID = request.roomType + "-" + (allocatedRooms.size() + 1);
                if (allocatedRooms.add(roomID)) { // Prevents double booking [cite: 8, 20]
                    inventory.put(request.roomType, inventory.get(request.roomType) - 1);
                    roomTypeMapping.computeIfAbsent(request.roomType, k -> new ArrayList<>()).add(roomID);
                    System.out.println("Booking confirmed for Guest: " + request.guestName +
                            ", Room ID: " + roomID);
                }
            } else {
                System.out.println("Booking failed for " + request.guestName + ": No " + request.roomType + " rooms available.");
            }}}
    public void addRequest(String name, String type) {
        requestQueue.add(new BookingRequest(name, type));
    }public static void main(String[] args) {
        BookMyStayApp system = new BookMyStayApp();
        system.addRequest("Alice", "Single");
        system.addRequest("Bob", "Double");
        system.addRequest("Charlie", "Suite");
        System.out.println("--- Starting Room Allocation ---");
        system.processAllocations();}}