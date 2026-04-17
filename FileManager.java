// FILE I/O CLASS
package AppointmentSchedulingSystem;
import java.io.PrintWriter;

public class FileManager {
    // TODO: Field: filename String
    
    // Method: saveToFile(Appointment[] list, int count) [Geoff]
    // TODO: Logic: Use PrintWriter/FileWriter to write array to .txt file
    saveToFile(Appointment[] list, int count){
        PrintWriter outFile = new PrintWriter("Schedule");
		
		for(i = 0; i <= count; i++){
			outFile.println(Appointment[i].name);
			outFile.println(Appointment[i].date);
			outFile.println(Appointment[i].time);
			outFile.println();
		}
    
}
