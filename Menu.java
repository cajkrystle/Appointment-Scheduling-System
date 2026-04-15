// UI / INPUT CLASS
package AppointmentSchedulingSystem;

public class Menu {
  public int menuChoice = 0;

    //constructor for some reason
Menu(int menuChoice){
this.menuChoice = menuChoice;
}

    public static void display(){
       System.out.println("---MENU---");
           System.out.println("1.Add Appointment");
           System.out.println("2.View Appointments");
           System.out.println("3.Check Conflicts");
           System.out.println("4.Cancel Appointments");
           System.out.println("5.Sort Appointments");
           System.out.println("6.Save Schedule");
           System.out.println("7.Exit");
    }

    

}
