// MAIN METHOD
// 4-305 [Castro, Rulida, Salcedo, Justin]
package AppointmentSchedulingSystem;

public class Main {
    public static void main(String[] args) {
        // TODO: Instantiate AppointmentScheduler, FileManager, and Menu objects
        
        // (display) MENU loop [BJ]
            // (input) ask for user choice 1-7 [BJ]
            
            // (switch-case)
                // case 1: Add Appointment [Geoff]
                    // (input) get name, date, and time from user
                    // (logic) call hasConflict(date, time)
                        // if conflict -> display warning, back to menu
                        // else -> add to array, display confirm msg
                        
                // case 2: View Appointments [AJ]
                    // (logic) call viewAppointments()
                    
                // case 3: Check Conflicts [Justin]
                    // (logic) call checkConflicts() to scan entire array
                    
                // case 4: Cancel Appointment [Geoff]
                    // (input) get name/index to remove
                    // (logic) call cancelAppointment()
                    
                // case 5: Sort Appointments [Geoff]
                    // (logic) call sortAppointments()
                    
                // case 6: Save Schedule [Geoff]
                    // (logic) call saveToFile()
                    
                // case 7: Exit
                    // (logic) break loop, close scanner
    }
}