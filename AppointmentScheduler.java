// CORE LOGIC CLASS
package AppointmentSchedulingSystem;
import java.util.Scanner;

public class AppointmentScheduler {
    // TODO: Fields: Appointment[] array, int count, static int MAX_SIZE
    private String[] appointments = new String[];
    private int count;
    private int MAX_SIZE;
    // TODO: Constructor: initialize array and count
    AppointmentScheduler {
        
        this.count = 0;
        this.appointments = new String[]
        this.MAX_SIZE = 100;
        
    }

    
    // Method: addAppointment(name, date, time) [Geoff]
    // TODO: Logic: Create new Appointment object, save to array, increment count
    static int addAppointment(){
        
        Scanner scan = new Scanner(System.in);
        
        if(count == MAX_SIZE){
            System.out.println("Error: Reached limit already");
            break;
        }
        
        System.out.println("Enter Date:");
        String date = scan.nextLine;
        System.out.println("Enter Time:");
        String time = scan.nextLine;
        System.out.println("Enter Name:");
        String name = scan.nextLine;

        boolean con = hasConflict(date, time);

        if(con == true){
            System.out.println("Error: Appointment already saved");
            break;
        }
        
        Appointment appoint = new Appointment(name, date, time);
        
        appointments[count].date = date;
        appointments[count].time = time;
        appointments[count].name = name;
        
        count++;
        
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
    public void viewAppointments() {
        System.out.println("\n--- Current Appointments ---");
        for (int i = 0; i < count; i++) {
            System.out.println(appointments[i].toString());
        }
    }
    
    // Method: cancelAppointment(name) [Geoff]
    // TODO: Logic: Find index of name, remove it, and shift array items left
    static int cancelAppointment(){
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Enter Name:");
        String name = scan.nextLine;
            
        for (int i = 0; i < count; i++) {
            if(appointments[i].name.equals(name)){
                for (int j = i; j < count; j++){
                    appointments[j].date = appointments[j+1].date;
                    appointments[j].time = appointments[j+1].time;
                    appointments[j].name = appointments[j+1].name;
                }  
            }      
        }   
        count--; 
    }
    
    // Method: sortAppointments() [Geoff]
    sortAppointments(){
        
        for (int i = 0; i < count; i++) {

            
        }
        
    }
    // TODO: Logic: Swap-sort algorithm to order appointments
}
