import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {
    
    private static final String USERS_FILE = "users.dat";
    private static final String EVENTS_FILE = "events.dat";
    private static final String JOINED_FILE = "joined.dat";
    private static DataManager instance;
    
    private final List<User> users;
    private final List<Event> postedEvents;
    private final List<Event> joinedEvents;

    private DataManager() {
        users = loadData(USERS_FILE);
        postedEvents = loadData(EVENTS_FILE);
        joinedEvents = loadData(JOINED_FILE);
        
        if (users.isEmpty()) {
            users.add(new User("admin", "1234", "Admin User", "admin@ecovibe.com"));
            saveUsers();
        }
        if (postedEvents.isEmpty()) {
            postedEvents.add(new Event("Community Clean Up", "Dec 20, 2025", "Clean Drive", "45", "Active", "Central Park", "Join us for a morning cleanup session."));
            postedEvents.add(new Event("Recycling Workshop", "Feb 10, 2026", "Education", "120", "Upcoming", "City Library", "Learn how to recycle effectively."));
            postedEvents.add(new Event("Mangrove Planting", "Mar 05, 2026", "Restoration", "85", "Planning", "Bay Area", "Restoring our coastal defense."));
            postedEvents.add(new Event("Beach Cleanup", "Apr 22, 2026", "Clean Drive", "200", "Pending", "Sunset Beach", "Annual Earth Day beach sweep."));
            saveEvents();
        }
    }
    
    public static DataManager getInstance() {
        if (instance == null) instance = new DataManager();
        return instance;
    }

    public boolean registerUser(String username, String password, String name, String email) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) return false;
        }
        users.add(new User(username, password, name, email));
        saveUsers();
        return true;
    }

    public boolean validateLogin(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) return true;
        }
        return false;
    }

    public List<Event> getPostedEvents() { return postedEvents; }
    public List<Event> getJoinedEvents() { return joinedEvents; }
    
    // Get available events (excluding joined ones)
    public List<Event> getAvailableEvents() {
        return postedEvents.stream()
            .filter(p -> joinedEvents.stream().noneMatch(j -> isSameEvent(p, j)))
            .collect(Collectors.toList());
    }
    
    public void addEvent(Event e) {
        postedEvents.add(e);
        saveEvents();
    }

    public void joinEvent(Event e) {
        if (joinedEvents.stream().noneMatch(j -> isSameEvent(j, e))) {
            joinedEvents.add(e);
            saveJoinedEvents();
        }
    }
    
    public void leaveEvent(Event e) {
        joinedEvents.removeIf(j -> isSameEvent(j, e));
        saveJoinedEvents();
    }
    
    private boolean isSameEvent(Event a, Event b) {
        return a.getName().equals(b.getName()) && a.getDate().equals(b.getDate());
    }
    
    // --- Unified Search Logic ---
    public List<Event> searchEventsUnified(String query) {
        String lowerQuery = query.toLowerCase();
        return getAvailableEvents().stream()
            .filter(e -> e.getName().toLowerCase().contains(lowerQuery) || 
                         e.getDate().toLowerCase().contains(lowerQuery) || 
                         (e.getLocation() != null && e.getLocation().toLowerCase().contains(lowerQuery)))
            .collect(Collectors.toList());
    }

    private void saveUsers() { saveData(users, USERS_FILE); }
    private void saveEvents() { saveData(postedEvents, EVENTS_FILE); }
    private void saveJoinedEvents() { saveData(joinedEvents, JOINED_FILE); }

    private <T> void saveData(List<T> data, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> loadData(String filename) {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}