// FILE I/O CLASS
package AppointmentSchedulingSystem;
import java.io.PrintWriter;

public class FileManager {
    // TODO: Field: filename String
    
    // Method: saveToFile(Appointment[] list, int count) [Geoff]
    // TODO: Logic: Use PrintWriter/FileWriter to write array to .txt file
    saveToFile(Appointment[] list, int count){
        int o = count;
        PrintWriter outFile = new PrintWriter("Schedule");
			
    	outFile.println(Appointment[o]);
		
    
}
