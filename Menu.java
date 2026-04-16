// UI / INPUT CLASS
package AppointmentSchedulingSystem;
  import java.util.Scanner;
public class Menu {
   public int menuChoice;

      Menu(int menuChoice){
       this.menuChoice = menuChoice;

      }
  Scanner scanner = new Scanner(System.in);
    
    // Method: display() [BJ]
    // TODO: Logic: Print the 1-7 menu options

   public void display(){
       System.out.println("-----MENU OPTIONS-----");
      System.out.println("1.Add Appointments");
      System.out.println("2.View Appointments");
      System.out.println("3.Check Conflicts");
      System.out.println("4.Cancel Appointments");
      System.out.println("5.Sort Appointments");
      System.out.println("6.Save Schedule");
      System.out.println("7.Exit");
     


   }
 
   
    // Method: getChoice() [BJ]
    // TODO: Logic: Read integer, handle invalid inputs
      public int getChoice(){
        String menuRemarks = " " ;
           if(menuChoice < 1 && menuChoice > 7){
              menuRemarks = " Invalid Choice.";
           }
      }
   
    // Method: getInput(prompt) [BJ]
    // TODO: Logic: Print prompt, return user's string input
      public String getInput(){
           


      }
    

    

}
