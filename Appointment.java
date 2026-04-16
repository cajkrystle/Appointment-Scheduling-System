// DATA OBJECT CLASS
package AppointmentSchedulingSystem;

public class Appointment {
    // TODO: Fields: name, date, time [AJ]
    private String name;
    private String date;
    private String time;
    
    // TODO: Constructor: initialize the fields
    public Appointment(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
    
    // TODO: Getters: getName(), getDate(), getTime() [AJ]
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    
    // TODO: Method: toString() 
    public String toString() {
        return String.format("%-15s | %-12s | %-8s", name, date, time);
    }
}
