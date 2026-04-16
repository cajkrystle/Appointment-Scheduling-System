// CORE LOGIC CLASS
package AppointmentSchedulingSystem;

public class AppointmentScheduler {
    // TODO: Fields: Appointment[] array, int count, static int MAX_SIZE
    
    // TODO: Constructor: initialize array and count
    
    // Method: addAppointment(name, date, time) [Geoff]
    // TODO: Logic: Create new Appointment object, save to array, increment count
    static int addAppointment(String name, String date, String time){
        
        
    }
    
    // Method: hasConflict(date, time) [Justin]
    public boolean hasConflict(String date, String time) {
        for (int i = 0; i < count; i++) {
            if (appointments[i].date.equals(date) &&
                appointments[i].time.equals(time)) {
                return true;
            }
        }
        return false;
    }
    
    // Method: checkConflicts() [Justin]
    public void checkConflicts() {
        boolean found = false;

        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {

                if (appointments[i].date.equals(appointments[j].date) &&
                    appointments[i].time.equals(appointments[j].time)) {

                    System.out.println("Conflict Found:");
                    System.out.println(appointments[i]);
                    System.out.println(appointments[j]);
                    System.out.println();

                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No conflicts found.");
        }
    }
    
    // Method: viewAppointments() [AJ]
    // TODO: Logic: Loop through array and print each Appointment
    
    // Method: cancelAppointment(name) [Geoff]
    // TODO: Logic: Find index of name, remove it, and shift array items left
    
    // Method: sortAppointments() [Geoff]
    // TODO: Logic: Swap-sort algorithm to order appointments
}
