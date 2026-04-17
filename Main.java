// MAIN METHOD
// 4-305 [Castro, Rulida, Salcedo, Justin]
package AppointmentSchedulingSystem;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager file = new FileManager("Appointments.txt");
        AppointmentScheduler scheduler = new AppointmentScheduler();
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
                case 1:
                    scheduler.addAppointments();
                    scheduler.checkConflicts();
                    break;
                    // (input) get name, date, and time from user
                    // (logic) call hasConflict(date, time)
                        // if conflict -> display warning, back to menu
                        // else -> add to array, display confirm msg
                        
                // case 2: View Appointments [AJ]
                case 2: // View Appointments [AJ]
                    scheduler.viewAppointments();
                    break;
        
                    
                case 3: // [Justin]
                    scheduler.checkConflicts();
                    break;
                    
                // case 4: Cancel Appointment [Geoff]
                    // (input) get name/index to remove
                case 4:
                    scheduler.cancelAppointment():
                    break;
                    // (logic) call cancelAppointment()
                    
                // case 5: Sort Appointments [Geoff]
                    // (logic) call sortAppointments()
                    
                // case 6: Save Schedule [Geoff]
                case 6:
                    file.saveToFile(appointment[], count);
                break;
                    // (logic) call saveToFile()
                    
                // case 7: Exit
                case 7: // Exit
                    System.out.println("Exiting system...");
                    running = false;
                    menu.close();
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
    }
}
