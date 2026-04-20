// MENU CLASS — kept for reference, no longer called by Main
// 4-305 [Castro, Rulida, Salcedo, Timbal]

package AppointmentSchedulingSystem;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void display() {
        System.out.println("\n***** MENU *****");
        System.out.println("1. Add Appointment");
        System.out.println("2. View Appointments");
        System.out.println("3. Check Conflicts");
        System.out.println("4. Cancel Appointment");
        System.out.println("5. Sort Appointments");
        System.out.println("6. Save Schedule");
        System.out.println("7. Exit");
    }

    public int getChoice() {
        try {
            System.out.print("Choose an option: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
