// MAIN METHOD
// 4-305 [Castro, Rulida, Salcedo, Justin]
package AppointmentSchedulingSystem;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // TODO: Instantiate AppointmentScheduler, FileManager, and Menu objects
        
        int menuChoice = 0;
       do{
           System.out.println("---MENU---");
           System.out.println("1.Add Appointment");
           System.out.println("2.View Appointments");
           System.out.println("3.Check Conflicts");
           System.out.println("4.Cancel Appointments");
           System.out.println("5.Sort Appointments");
           System.out.println("6.Save Schedule");
           System.out.println("7.Exit");
           System.out.print("Enter Choice: ");
          menuChoice = scanner.nextInt();

           if(menuChoice == 7){
            System.out.println("Goodbye!");
               break;
           }
           
       }
       while(menuChoice != 7);
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
