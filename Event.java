import java.io.Serializable;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String date;
    private String type;
    private String attendees;
    private String status;
    private String location;    // New Field
    private String description; // New Field
    private String imagePath;   // New Field

    public Event(String name, String date, String type, String attendees, String status, String location, String description) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.attendees = attendees;
        this.status = status;
        this.location = location;
        this.description = description;
        this.imagePath = null;
    }

    // Getters & Setters
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getType() { return type; }
    public String getAttendees() { return attendees; }
    public String getStatus() { return status; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    
    public void setImagePath(String path) { this.imagePath = path; }
}