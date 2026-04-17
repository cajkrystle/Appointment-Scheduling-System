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
        System.out.println("Enter Choice: ");


        if(scanner.hasNextInt()){
          menuChoice = scanner.nextInt();
          scanner.nextLine();


        
           if(menuChoice < 1 || menuChoice > 7){
              System.out.println("Invalid Choice. Enter 1 - 7 only.");
             return - 1;
           }
           
        return menuChoice;
        } else{
         System.out.println("Invalid. Pls enter a number: ");
        scanner.nextLine();
        return - 1;
      }
      }

    // Method: getInput(prompt) [BJ]
    // TODO: Logic: Print prompt, return user's string input
      public String getInput(String prompt){
        System.out.print(prompt);
          return scanner.nextLine();
}


      
    

    

}
